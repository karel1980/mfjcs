package org.mfjcs.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Collections;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.inmemory.InMemoryTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mfjcs.api.IndexOperationFailedException;
import org.mfjcs.api.Item;
import org.mfjcs.api.MFJCSService;
import org.mfjcs.core.ItemMetadataImpl;
import org.mfjcs.core.ItemNotFoundException;

import com.fasterxml.jackson.databind.node.ObjectNode;

import io.dropwizard.testing.junit.ResourceTestRule;

public class ItemResourceTest {

	private MFJCSService service = mock(MFJCSService.class);

	private Item item = mock(Item.class);

	@Rule
	public ResourceTestRule resourceTestRule = ResourceTestRule.builder()
			.addResource(new ItemResource(service))
			.setTestContainerFactory(new InMemoryTestContainerFactory() {
				@Override
				public TestContainer create(URI baseUri, DeploymentContext context) {
					return super.create(UriBuilder.fromUri(baseUri).path("v1/api/").build(), context);
				}
			})
			.build();

	@Before
	public void setUp() throws ItemNotFoundException, IndexOperationFailedException {
		when(service.getItem("itemId")).thenReturn(item);
		when(item.getFields()).thenReturn(Collections.singletonMap("foo", "bar"));
		when(item.getMetadata()).thenReturn(new ItemMetadataImpl("itemId", 123L, "bob"));
	}

	@Test
	public void getItemReturnsItemAsJson() {
		Response response = resourceTestRule.client().target("/v1/api/items/itemId").request().get();
		ObjectNode json = response.readEntity(ObjectNode.class);

		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
		assertThat(json.get("fields").get("foo").asText()).isEqualTo("bar");
		assertThat(json.get("metadata").get("href").asText()).isEqualTo("/v1/api/items/itemId");
		assertThat(json.get("metadata").get("sys_version").asLong()).isEqualTo(123L);
		assertThat(json.get("metadata").get("sys_author").asText()).isEqualTo("bob");
	}

}