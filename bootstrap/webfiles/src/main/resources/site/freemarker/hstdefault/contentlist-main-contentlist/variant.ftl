<#include "../../include/imports.ftl">

<#-- @ftlvariable name="pageable" type="org.onehippo.cms7.essentials.components.paging.Pageable" -->
<#-- @ftlvariable name="item" type="com.incentro.beans.ContentDocument" -->
<#if pageable?? && pageable.items?has_content>
  <div>
    <#list pageable.items as item>
      <div class="media has-edit-button">
        <@hst.cmseditlink hippobean=item/>
        <div class="media-body">
          <h4 class="media-heading">
            <@hst.link var="link" hippobean=item/>
            <a href="${link}">${item.title?html}</a>
            <#if item.publicationDate?? && item.publicationDate.time??>
              <span class="label label-success pull-right">
                <@fmt.formatDate value=item.publicationDate.time type="both" dateStyle="medium" timeStyle="short"/>
              </span>
            </#if>
          </h4>
          <#if item.introduction??>
            <p>${item.introduction?html}</p>
          </#if>
        </div>
      </div>
    </#list>
    <#if cparam.showPagination>
      <#include "../../include/pagination.ftl">
    </#if>
  </div>
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->
<#elseif editMode>
  <div>
    <img src="<@hst.link path='/images/essentials/catalog-component-icons/generic-list.png'/>"> Click to edit Content list
  </div>
</#if>
