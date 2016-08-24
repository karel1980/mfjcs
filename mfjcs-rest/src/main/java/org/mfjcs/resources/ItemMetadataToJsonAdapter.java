package org.mfjcs.resources;

import javax.ws.rs.core.UriBuilder;

import org.mfjcs.api.ItemMetadata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemMetadataToJsonAdapter {

	private ItemMetadata itemMetadata;

	public ItemMetadataToJsonAdapter(ItemMetadata itemMetadata) {
		this.itemMetadata = itemMetadata;
	}

	@JsonProperty
	public String getHref() {
		return UriBuilder.fromPath("/v1/api/items/").path(itemMetadata.getId()).build().toString();
	}

	@JsonProperty("sys_version")
	public Long getVersion() { return itemMetadata.getVersion(); }

	@JsonProperty("sys_author")
	public String getAuthor() { return itemMetadata.getAuthor(); }

}
