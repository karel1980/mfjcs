package org.mfjcs.resources;

import java.util.Map;

import org.mfjcs.api.Item;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemToJsonAdapter {

	private Item item;
	private ItemMetadataToJsonAdapter itemMetadataToJsonAdapter;

	public ItemToJsonAdapter(Item item, ItemMetadataToJsonAdapter itemMetadataToJsonAdapter) {
		this.item = item;
		this.itemMetadataToJsonAdapter = itemMetadataToJsonAdapter;
	}

	@JsonProperty
	public ItemMetadataToJsonAdapter getMetadata() {
		return itemMetadataToJsonAdapter;
	}

	@JsonProperty
	public Map<String, Object> getFields() {
		return item.getFields();
	}
}
