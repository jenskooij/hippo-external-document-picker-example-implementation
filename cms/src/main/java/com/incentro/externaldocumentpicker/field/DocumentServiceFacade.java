/**
 * Copyright 2014 Hippo B.V. (http://www.onehippo.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.incentro.externaldocumentpicker.field;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Value;

import org.apache.commons.lang.StringUtils;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentCollection;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentServiceContext;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentServiceFacade;
import org.onehippo.forge.exdocpicker.impl.SimpleExternalDocumentCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * Example trivial implementation of <code>ExternalDocumentServiceFacade</code> for developer's reference.
 *
 * <P>
 * This example simply reads all the document data as <code>JSONArray</code> containing multiple <code>JSONObject</code> instances.
 * from <code>classpath:org/onehippo/forge/exdocpicker/demo/field/DocumentServiceFacade.json</code>.
 * </p>
 * <p>
 * This example simply searches external documents from the loaded <code>JSONArray</code> member.
 * </p>
 * <p>
 * And, this example reads/sets JCR document node field to string array of the selected external document IDs.
 * The field name can be configured in the plugin configuration; this example reads 'example.external.docs.field.name' plugin parameter
 * to get the physical field name of the document node.
 * </p>
 */
public class DocumentServiceFacade implements ExternalDocumentServiceFacade<JSONObject> {

    /**
     * Plugin parameter name for physical document field name (JCR property name).
     */
    public static final String PARAM_EXTERNAL_DOCS_FIELD_NAME = "example.external.docs.field.name";
    public static final String JSON_REST_URL = "http://localhost:8080/site/binaries/content/assets/examples/exdocs.json";

    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(DocumentServiceFacade.class);

    private JSONArray docArray;

    public DocumentServiceFacade() {
        getDocumentsFromExternalSource();
    }

    /**
     * Make a simple request to your rest webservice
     *
     * @param queryString
     */
    private void getDocumentsFromExternalSource(String queryString) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        try {
            String result = restTemplate.getForObject(JSON_REST_URL + "?q=" + queryString, String.class);
            docArray = (JSONArray) JSONSerializer.toJSON(result);
            log.info("Retrieved {} documents from {}", docArray.size(), JSON_REST_URL + "?q=" + queryString);
        } catch (Exception e) {
            log.warn("Couldnt get from endpoint: {}", e.getMessage());
        }
    }

    private void getDocumentsFromExternalSource() {
        getDocumentsFromExternalSource("");
    }

    @Override
    public ExternalDocumentCollection<JSONObject> searchExternalDocuments(ExternalDocumentServiceContext context, String queryString) {
        ExternalDocumentCollection<JSONObject> docCollection = new SimpleExternalDocumentCollection<JSONObject>();
        getDocumentsFromExternalSource(queryString);
        for (int i = 0; i < docArray.size(); i++) {
            docCollection.add(docArray.getJSONObject(i));
        }
        return docCollection;
    }

    @Override
    public ExternalDocumentCollection<JSONObject> getFieldExternalDocuments(ExternalDocumentServiceContext context) {
        final String fieldName = context.getPluginConfig().getString(PARAM_EXTERNAL_DOCS_FIELD_NAME);

        if (StringUtils.isBlank(fieldName)) {
            throw new IllegalArgumentException("Invalid plugin configuration parameter for '" + PARAM_EXTERNAL_DOCS_FIELD_NAME + "': " + fieldName);
        }

        ExternalDocumentCollection<JSONObject> docCollection = new SimpleExternalDocumentCollection<JSONObject>();

        try {
            final Node contextNode = context.getContextModel().getNode();

            if (contextNode.hasProperty(fieldName)) {
                Value[] values = contextNode.getProperty(fieldName).getValues();

                // This is where it checks which values are selected.
                for (Value value : values) {
                    String id = value.getString();
                    JSONObject doc = findDocumentById(id);

                    if (doc != null) {
                        docCollection.add(doc);
                    }
                }
            }
        } catch (RepositoryException e) {
            log.error("Failed to retrieve related exdoc array field.", e);
        }

        return docCollection;
    }

    /**
     * This method sets what data form the external source should be stored on the document.
     * In this case it stores the entire json object as string
     *
     * @param context
     * @param exdocs
     */
    @Override
    public void setFieldExternalDocuments(ExternalDocumentServiceContext context, ExternalDocumentCollection<JSONObject> exdocs) {
        final String fieldName = context.getPluginConfig().getString(PARAM_EXTERNAL_DOCS_FIELD_NAME);

        if (StringUtils.isBlank(fieldName)) {
            throw new IllegalArgumentException("Invalid plugin configuration parameter for '" + PARAM_EXTERNAL_DOCS_FIELD_NAME + "': " + fieldName);
        }

        try {
            final Node contextNode = context.getContextModel().getNode();
            final List<String> docIds = new ArrayList<String>();

            for (Iterator<? extends JSONObject> it = exdocs.iterator(); it.hasNext(); ) {
                JSONObject doc = it.next();
                docIds.add(doc.toString());
            }

            contextNode.setProperty(fieldName, docIds.toArray(new String[docIds.size()]));
        } catch (RepositoryException e) {
            log.error("Failed to set related exdoc array field.", e);
        }
    }

    @Override
    public String getDocumentTitle(ExternalDocumentServiceContext context, JSONObject doc, Locale preferredLocale) {
        if (doc != null && doc.has("title")) {
            return doc.getString("title");
        }

        return "";
    }

    @Override
    public String getDocumentDescription(ExternalDocumentServiceContext context, JSONObject doc, Locale preferredLocale) {
        if (doc != null && doc.has("description")) {
            return doc.getString("description");
        }

        return "";
    }

    @Override
    public String getDocumentIconLink(ExternalDocumentServiceContext context, JSONObject doc, Locale preferredLocale) {
        if (doc != null && doc.has("icon")) {
            return doc.getString("icon");
        }

        return "";
    }
    
    private JSONObject findDocumentById(final String id) {
        for (int i = 0; i < docArray.size(); i++) {
            JSONObject doc = docArray.getJSONObject(i);

            if (StringUtils.equals(id, doc.toString())) {
                return doc;
            }
        }

        return null;
    }

}
