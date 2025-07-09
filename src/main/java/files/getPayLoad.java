package files;

public class getPayLoad {

	public static String userDetails(String UserName, String Password)
	{
		return "{\"username\": \"" + UserName + "\", \"password\": \""+ Password + "\"}";
	}

	public static String customerCode(String customerCode)
	{
		return "{\n"
				+ "	\"customerCode\": \"" + customerCode + "\" \n" 
				+ "}";
	}
	
	public static String freeTimeDnd(String ShipmentNumber)
	{
		// Get current date and add 30 days for the requested date
		java.time.LocalDate currentDate = java.time.LocalDate.now();
		java.time.LocalDate requestedDate = currentDate.plusDays(30);
		String formattedDate = requestedDate.toString() + "T00:00:00.000Z"; // format YYYY-MM-DDT00:00:00.000Z for ISO format
		
	
		
		return "{\n"
				+ "  \"billOfLadingId\": \""+ShipmentNumber+"\",\n"
				+ "  \"carrierCode\": \"MAEU\",\n"
				+ "  \"chargeType\": \"DMR\",\n"
				+ "  \"containerDetails\": [],\n"
				+ "  \"customerCode\": \"10000007951\",\n"
				+ "  \"requestedDateTime\": \""+formattedDate+"\",\n"
				+ "  \"motherShipmentId\": \""+ShipmentNumber+"\",\n"
				+ "  \"chargesFromDate\": null,\n"
				+ "  \"chargesToDate\": null\n"
				+ "}";
	
	}

	

	public static String bookingConfirmed(String receiverId, String bookingReferenceId, String bookingNumber,String bookingStatus) {
		// TODO Auto-generated method stub
		return "{\n"
				+ "    \"eventType\": \"BOOKING_STATUS\",\n"
				+ "    \"isSilent\": false,\n"
				+ "    \"receiverType\": \"DEVICE\",\n"
				+ "    \"receiverId\": \""+receiverId+"\",\n"
				+ "    \"extras\": {\n"
				+ "        \"bookingReferenceId\": \""+bookingReferenceId+"\",\n"
				+ "        \"bookingNumber\": \""+bookingNumber+"\",\n"
				+ "        \"bookingStatus\": \""+bookingStatus+"\"\n"
				+ "    }\n"
				+ "}";
	}

	public static String shipmentArrival(String receiverId, String shpmentId, String Arrivaldate) {
		// TODO Auto-generated method stub
		return "{\n"
				+ "    \"eventType\": \"PRE_ARRIVAL_NOTICE\",\n"
				+ "    \"isSilent\": false,\n"
				+ "    \"receiverType\": \"DEVICE\",\n"
				+ "    \"receiverId\": \""+receiverId+"\",\n"
				+ "    \"extras\": {\n"
				+ "        \"shipmentId\": \""+shpmentId+"\",\n"
				+ "        \"operatorCode\": \"MAEU\",\n"
				+ "        \"vessel\": \"Q4D\",\n"
				+ "        \"voyage\": \"204E\",\n"
				+ "        \"arrivalPort\": \"Savannah, United States\",\n"
				+ "        \"cargoDueToArrive\": \""+Arrivaldate+"\",\n"
				+ "        \"bolNumber\": \""+shpmentId+"\"\n"
				+ "    }\n"
				+ "}";
	}

	public static String shipmentDeparture(String receiverId, String shpmentId, String vesselDepartTime) {
		return "{\n"
				+ "    \"eventType\": \"VESSEL_DEPARTURE\",\n"
				+ "    \"isSilent\": false,\n"
				+ "    \"receiverType\": \"DEVICE\",\n"
				+ "    \"receiverId\": \""+receiverId+"\",\n"
				+ "    \"extras\": {\n"
				+ "        \"shipmentId\": \""+shpmentId+"\",\n"
				+ "        \"operatorCode\": \"MAEU\",\n"
				+ "        \"vessel\": \"Q4D\",\n"
				+ "        \"voyage\": \"204E\",\n"
				+ "        \"departurePort\": \"Savannah, United States\",\n"
				+ "        \"vesselDepartTime\": \""+vesselDepartTime+"\",\n"
				+ "        \"bolNumber\": \""+shpmentId+"\"\n"
				+ "    }\n"
				+ "}";
	}

	public static String shipmentETA(String receiverId, String shpmentId, String etaDate) {
		return "{\n"
				+ "    \"eventType\": \"Shipment_ETA\",\n"
				+ "    \"isSilent\": false,\n"
				+ "    \"receiverType\": \"DEVICE\",\n"
				+ "    \"receiverId\": \""+receiverId+"\",\n"
				+ "   \"extras\": {\n"
				+ "        \"shipmentId\": \""+shpmentId+"\",\n"
				+ "        \"operatorCode\": \"MAEU\",\n"
				+ "        \"portFirstLoad\": \"Abu Dhabi, United Arab Emirates\",\n"
				+ "        \"portLastDischarge\": \"Jawaharlal Nehru, India\",\n"
				+ "        \"etaNote\": \"DELAYED\",\n"
				+ "        \"etaReason\": \"Change of discharge port\",\n"
				+ "        \"etaRevised\": \""+etaDate+"\",\n"
				+ "        \"bolNumber\": \""+shpmentId+"\"\n"
				+ "    }\n"
				+ "}";
	}

	public static String shipmentCancled(String receiverId, String shpmentId) {
		
		return "{\n"
				+ "    \"eventType\": \"CANCELLED_SHIPMENT\",\n"
				+ "    \"isSilent\": false,\n"
				+ "    \"receiverType\": \"DEVICE\",\n"
				+ "    \"receiverId\": \""+receiverId+"\",\n"
				+ "   \"extras\": {\n"
				+ "        \"shipmentId\": \""+shpmentId+"\",\n"
				+ "        \"operatorCode\": \"MAEU\",\n"
				+ "        \"bolNumber\": \""+shpmentId+"\"\n"
				+ "    }\n"
				+ "}";
	}

	public static String spotAvailability(String receiverId, String subscriptionId, String loadService, String dischargeService) {
		// TODO Auto-generated method stub
		return "{\n"
				+ "    \"eventType\": \"SPOT_AVAILABLE\",\n"
				+ "    \"isSilent\": false,\n"
				+ "    \"receiverType\": \"DEVICE\",\n"
				+ "    \"receiverId\": \""+receiverId + "\",\n"
				+ "    \"extras\": {\n"
				+ "        \"originName\": \"Jebel Ali\",\n"
				+ "        \"origin\": \"AEJAL\",\n"
				+ "        \"destinationName\": \"Rotterdam\",\n"
				+ "        \"destination\": \"NLROT\",\n"
				+ "        \"equipmentSize\": \"40 DRY\",\n"
				+ "        \"subscriptionId\": \""+subscriptionId+ "\",\n"
				+ "        \"routingUrl\": \"https://www.maersk.com/mobile/spotAvailable\",\n"
				+ "  \"eventName\": \"SPOT_AVAILABLE\",\n"
				+ "  \"originCountryCode\": \"AE\",\n"
				+ "  \"originGeoIdCode\": \"31RTK5H2BLBS3\",\n"
				+ "  \"originMaerskRkstCode\": \"JAL\",\n"
				+ "  \"originCityName\": \"Jebel Ali\",\n"
				+ "  \"destinationCountryCode\": \"NL\",\n"
				+ "  \"destinationCityName\": \"Rotterdam\",\n"
				+ "  \"destinationMaerskRkstCode\": \"ROT\",\n"
				+ "  \"destinationGeoIdCode\": \"0QKGIC8VAS51A\",\n"
				+ "  \"loadService\": \""+loadService+"\",\n"
				+ "  \"dischargeService\": \""+dischargeService+"\" \n"
				+ "    }\n"
				+ "}";
	}

	
	public static String gateWayDepartures(String[] orgLocation, String[] dstLocation, String exportServiceMode,
			String importServiceMode, String currentDate) {
		// TODO Auto-generated method stub
		
		
		
		String orgGeoId = orgLocation[0];
		String orgRkstCode = orgLocation[1];
		String orgCountryCode = orgLocation[2];
		
		
		String dstGeoId = dstLocation[0];
		String dstRkstCode = dstLocation[1];
		String dstCountryCode = dstLocation[2];
		
		return "{\n"
				+ "  \"bookingType\": \"NEW\",\n"
				+ "  \"originLocation\": {\n"
				+ "    \"geoId\": \""+ orgGeoId + "\",\n"
				+ "    \"rkstCode\": \""+ orgRkstCode + "\",\n"
				+ "    \"countryCode\": \"" + orgCountryCode+ "\"\n"
				+ "  },\n"
				+ "  \"destinationLocation\": {\n"
				+ "    \"geoId\": \""+dstGeoId+"\",\n"
				+ "    \"rkstCode\": \""+dstRkstCode+ "\",\n"
				+ "    \"countryCode\": \""+dstCountryCode+"\"\n"
				+ "  },\n"
				+ "  \"batchSizeInWeeks\": 2,\n"
				+ "  \"productsFetchSizeInWeeks\": 2,\n"
				+ "  \"validationFlags\": {\n"
				+ "    \"checkEquipmentAvailability\": true,\n"
				+ "    \"checkDeadlines\": true,\n"
				+ "    \"checkSpace\": true,\n"
				+ "    \"checkYield\": true,\n"
				+ "    \"checkFixedPrices\": true\n"
				+ "  },\n"
				+ "  \"carrierCode\": \"MAEU\",\n"
				+ "  \"exportServiceMode\": \""+ exportServiceMode+ "\",\n"
				+ "  \"importServiceMode\": \""+ importServiceMode + "\",\n"
				+ "  \"cargoDetails\": {\n"
				+ "    \"commodityCode\": \"004103\",\n"
				+ "    \"cargoType\": \"DRY\",\n"
				+ "    \"isDangerous\": false,\n"
				+ "    \"isTempControlled\": false\n"
				+ "  },\n"
				+ "  \"containerDetails\": [\n"
				+ "    {\n"
				+ "      \"size\": \"40\",\n"
				+ "      \"type\": \"DRY\",\n"
				+ "      \"height\": \"8 6\",\n"
				+ "      \"quantity\": 1,\n"
				+ "      \"cargoWeight\": 18000,\n"
				+ "      \"weightMeasurementUnit\": \"KGS\",\n"
				+ "      \"isNOR\": false,\n"
				+ "      \"isFoodGrade\": false,\n"
				+ "      \"isoCode\": \"42G1\",\n"
				+ "      \"isShipperOwned\": false,\n"
				+ "      \"isOutOfGauge\": false,\n"
				+ "      \"isImportReturned\": false\n"
				+ "    }\n"
				+ "  ],\n"
				+ "  \"isContainerPickedUp\": false,\n"
				+ "  \"isContainerGatedIn\": false,\n"
				+ "  \"earliestDepartureDate\": \""+currentDate+ "\",\n"
				+ "  \"priceCalculationDate\": \""+currentDate+"\",\n"
				+ "  \"parties\": [\n"
				+ "    {\n"
				+ "      \"scvCode\": \"10000007951\",\n"
				+ "      \"cmdCode\": \"DK00007951\",\n"
				+ "      \"roleName\": \"Booked By\",\n"
				+ "      \"companyName\": \"GCSS BOOKED BY\",\n"
				+ "      \"companyAddress\": \"50,  PAKHUS D, 3ND FLOOR,   DAMPFAERGEVEJ,  COPENHAGEN,  DENMARK,   2200 \",\n"
				+ "      \"roleCode\": 1\n"
				+ "    },\n"
				+ "    {\n"
				+ "      \"scvCode\": \"10000007951\",\n"
				+ "      \"cmdCode\": \"DK00007951\",\n"
				+ "      \"roleName\": \"Price Owner\",\n"
				+ "      \"roleCode\": 44\n"
				+ "    }\n"
				+ "  ],\n"
				+ "  \"buCode\": \"1522\",\n"
				+ "  \"productsEagerFetch\": false\n"
				+ "}";
	}

	
}
