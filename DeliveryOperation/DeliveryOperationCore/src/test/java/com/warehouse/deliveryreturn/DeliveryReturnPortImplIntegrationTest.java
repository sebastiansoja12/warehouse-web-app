package com.warehouse.deliveryreturn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestClient;

import com.warehouse.commonassets.enumeration.DeliveryStatus;
import com.warehouse.commonassets.enumeration.ProcessType;
import com.warehouse.commonassets.identificator.DepartmentCode;
import com.warehouse.commonassets.identificator.DeviceId;
import com.warehouse.commonassets.identificator.ShipmentId;
import com.warehouse.commonassets.identificator.SupplierCode;
import com.warehouse.deliveryreturn.domain.model.DeliveryReturnDetails;
import com.warehouse.deliveryreturn.domain.model.DeliveryReturnRequest;
import com.warehouse.deliveryreturn.domain.port.primary.DeliveryReturnPort;
import com.warehouse.deliveryreturn.domain.port.secondary.MailServicePort;
import com.warehouse.deliveryreturn.domain.port.secondary.ShipmentRepositoryServicePort;
import com.warehouse.deliveryreturn.domain.port.secondary.ShipmentStatusControlServicePort;
import com.warehouse.deliveryreturn.domain.vo.UpdateStatus;
import com.warehouse.deliveryreturn.domain.vo.UpdateStatusShipmentRequest;
import com.warehouse.deliveryreturn.infrastructure.adapter.secondary.exception.BusinessException;
import com.warehouse.deliveryreturn.infrastructure.adapter.secondary.exception.TechnicalException;
import com.warehouse.routelogger.RouteLogEventPublisher;
import com.warehouse.terminal.DeviceInformation;
import com.warehouse.tools.routelog.RouteTrackerLogProperties;
import com.warehouse.tools.shipment.ShipmentProperties;
import com.warehouse.tools.supplier.SupplierValidatorProperties;

@SpringBootTest(classes = DeliveryReturnPortImplIntegrationTest.DeliveryReturnPortIntegrationTestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeliveryReturnPortImplIntegrationTest {

	@ComponentScan(basePackages = { "com.warehouse.deliveryreturn" })
	@EntityScan(basePackages = { "com.warehouse.deliveryreturn" })
	@EnableJpaRepositories(basePackages = { "com.warehouse.deliveryreturn" })
	@EnableAutoConfiguration
	public static class DeliveryReturnPortIntegrationTestConfiguration {

		@MockBean
		public ShipmentStatusControlServicePort shipmentStatusControlServicePort;

		@MockBean
		public ShipmentProperties shipmentProperties;

		@MockBean
		public ShipmentRepositoryServicePort shipmentRepositoryServicePort;

		@MockBean
		public MailServicePort mailServicePort;

		@MockBean
		public SupplierValidatorProperties supplierValidatorProperties;

		@MockBean
		public RouteTrackerLogProperties routeTrackerLogProperties;

		@MockBean
		public RouteLogEventPublisher routeLogEventPublisher;

		@Bean
		public RestClient restClient(RestClient.Builder builder) {
			return builder.baseUrl("http://localhost:8080").build();
		}
	}

	@Autowired
	private DeliveryReturnPort deliveryReturnPort;

	@Autowired
	private RestClient restClient;

	@Autowired
	private ShipmentProperties shipmentProperties;

	@Autowired
	private ShipmentStatusControlServicePort shipmentStatusControlServicePort;

	@Autowired
	private ShipmentRepositoryServicePort shipmentRepositoryServicePort;

	@Autowired
	private MailServicePort mailServicePort;

	private final DeviceInformation deviceInformation = DeviceInformation.builder()
			.version("1")
			.deviceId(new DeviceId(1L))
			.username("s-soja")
			.departmentCode(new DepartmentCode("KT1"))
			.build();

	@BeforeEach
	void setup() {
		reset(shipmentStatusControlServicePort);
	}

	@Test
	void shouldBreakProcessWhenBusinessExceptionIsThrown() {
		// given
		final List<DeliveryReturnDetails> deliveryReturnDetails = buildReturnDetails(1L, DeliveryStatus.RETURN, "KT1",
				"abc", null);
		final DeliveryReturnRequest request = buildDeliveryReturnRequest(ProcessType.RETURN, deviceInformation,
				deliveryReturnDetails);

		final UpdateStatusShipmentRequest updateStatusShipmentRequest = new UpdateStatusShipmentRequest(1L);

		when(shipmentStatusControlServicePort.updateStatus(updateStatusShipmentRequest))
				.thenReturn(UpdateStatus.NOT_OK);
		doThrow(new BusinessException(404, "Parcel 1 was not found")).when(shipmentRepositoryServicePort)
				.downloadShipment(new ShipmentId(1L));
		// when
		final Executable executable = () -> deliveryReturnPort.deliverReturn(request);
		// then
		final BusinessException exception = assertThrows(BusinessException.class, executable);
		assertEquals("Parcel 1 was not found", exception.getMessage());
	}

	@Test
	void shouldBreakProcessWhenTechnicalExceptionIsThrown() {
		// given
		final List<DeliveryReturnDetails> deliveryReturnDetails = buildReturnDetails(1L, DeliveryStatus.RETURN, "KT1",
				"abc", null);
		final DeliveryReturnRequest request = buildDeliveryReturnRequest(ProcessType.RETURN, deviceInformation,
				deliveryReturnDetails);

		final UpdateStatusShipmentRequest updateStatusShipmentRequest = new UpdateStatusShipmentRequest(1L);

		when(shipmentStatusControlServicePort.updateStatus(updateStatusShipmentRequest)).thenReturn(UpdateStatus.OK);
		doThrow(new TechnicalException(500, "Could not establish connection")).when(shipmentRepositoryServicePort)
				.downloadShipment(new ShipmentId(1L));
		// when
		final Executable executable = () -> deliveryReturnPort.deliverReturn(request);
		// then
		final TechnicalException exception = assertThrows(TechnicalException.class, executable);
		assertEquals("Could not establish connection", exception.getMessage());
	}

	private DeliveryReturnRequest buildDeliveryReturnRequest(ProcessType processType,
			DeviceInformation deviceInformation, List<DeliveryReturnDetails> deliveryReturnDetails) {
		return new DeliveryReturnRequest(processType, deviceInformation, deliveryReturnDetails);
	}

	private List<DeliveryReturnDetails> buildReturnDetails(Long parcelId, DeliveryStatus deliveryStatus,
			String depotCode, String supplierCode, String token) {

		final DeliveryReturnDetails deliveryReturnDetails = DeliveryReturnDetails.builder()
				.deliveryStatus(deliveryStatus)
				.shipmentId(new ShipmentId(parcelId))
				.supplierCode(new SupplierCode(supplierCode))
				.departmentCode(new DepartmentCode(depotCode))
				.build();

		return List.of(deliveryReturnDetails);
	}
}
