<#include "../include/imports.ftl">

<#-- @ftlvariable name="pageable" type="org.onehippo.cms7.essentials.components.paging.Pageable" -->
<#-- @ftlvariable name="item" type="com.incentro.beans.ContentDocument" -->
<#if pageable?? && pageable.items?has_content>
  <div>
    <#list pageable.items as item>
      <article class="has-edit-button">
        <@hst.cmseditlink hippobean=item/>
        <@hst.link var="link" hippobean=item/>
        <h3><a href="${link}">${item.title?html}</a></h3>
        <h4>Contains ${item.externalDocument?size} kittens.</h4>
        <#if item.publicationDate??>
          <p>
            <@fmt.formatDate value=item.publicationDate.time type="both" dateStyle="medium" timeStyle="short"/>
          </p>
        </#if>
        <#if item.introduction??>
          <p>${item.introduction?html}</p>
        </#if>
      </article>
    </#list>
    <#if cparam.showPagination>
      <#include "../include/pagination.ftl">
    </#if>
  </div>
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->
<#elseif editMode>
  <div>
    <img src="<@hst.link path='/images/essentials/catalog-component-icons/generic-list.png'/>"> Click to edit Content list
  </div>
</#if>
