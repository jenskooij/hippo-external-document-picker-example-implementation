package com.incentro.beans;
/*
 * Copyright 2014-2015 Hippo B.V. (http://www.onehippo.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.Calendar;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

@HippoEssentialsGenerated(internalName = "externaldocumentpicker:contentdocument")
@Node(jcrType = "externaldocumentpicker:contentdocument")
public class ContentDocument extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "externaldocumentpicker:introduction")
    public String getIntroduction() {
        return getProperty("externaldocumentpicker:introduction");
    }

    @HippoEssentialsGenerated(internalName = "externaldocumentpicker:title")
    public String getTitle() {
        return getProperty("externaldocumentpicker:title");
    }

    @HippoEssentialsGenerated(internalName = "externaldocumentpicker:content")
    public HippoHtml getContent() {
        return getHippoHtml("externaldocumentpicker:content");
    }

    @HippoEssentialsGenerated(internalName = "externaldocumentpicker:publicationdate")
    public Calendar getPublicationDate() {
        return getProperty("externaldocumentpicker:publicationdate");
    }
}
