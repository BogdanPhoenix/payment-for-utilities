package org.university.payment_for_utilities.services.implementations.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;
import org.university.payment_for_utilities.configurations.database.DataBaseConfiguration;
import org.university.payment_for_utilities.pojo.requests.address.*;
import org.university.payment_for_utilities.pojo.responses.address.AddressResidenceResponse;
import org.university.payment_for_utilities.pojo.responses.address.SettlementNameResponse;
import org.university.payment_for_utilities.pojo.responses.address.SettlementResponse;
import org.university.payment_for_utilities.pojo.responses.address.TypeSettlementResponse;

@TestConfiguration
@ComponentScan(basePackages = "org.university.payment_for_utilities.services.implementations.address")
@Import(DataBaseConfiguration.class)
public class AddressEntitiesRequestTestContextConfiguration {
    @Autowired
    private AddressResidenceServiceImpl addressResidenceService;
    @Autowired
    private TypeSettlementServiceImpl typeService;
    @Autowired
    private SettlementNameServiceImpl nameService;
    @Autowired
    private SettlementServiceImpl settlementService;

    @Lazy
    @Bean(name = "addressKyivResidence")
    public AddressResidenceResponse addressKyivResidence(){
        return createAddress(addressKyivRequest());
    }

    @Lazy
    @Bean(name = "addressResidence")
    public AddressResidenceResponse addressResidence(){
        return createAddress(addressRivneRequest());
    }

    @Lazy
    @Bean(name = "addressRivneRequest")
    public AddressResidenceRequest addressRivneRequest(){
        var settlement = createSettlement(settlementRivneRequest());

        return AddressResidenceRequest
                .builder()
                .settlement(settlement.getId())
                .uaNameStreet("вулиця")
                .enNameStreet("street")
                .numHouse("5a")
                .numEntrance("1")
                .numApartment("305")
                .build();
    }

    @Lazy
    @Bean(name = "settlementKyivResponse")
    public SettlementResponse settlementKyivResponse() {
        return createSettlement(settlementKyivRequest());
    }

    @Lazy
    @Bean(name = "addressKyivRequest")
    public AddressResidenceRequest addressKyivRequest(){
        var settlement = settlementKyivResponse();

        return AddressResidenceRequest
                .builder()
                .settlement(settlement.getId())
                .uaNameStreet("вулиця нова")
                .enNameStreet("new street")
                .numHouse("4")
                .numEntrance("")
                .numApartment("")
                .build();
    }

    @Lazy
    @Bean(name = "typeSettlementVillageResponse")
    public TypeSettlementResponse typeSettlementVillageResponse() {
        return createTypeSettlement(typeSettlementVillageRequest());
    }

    @Lazy
    @Bean(name = "settlementNameKyivResponse")
    public SettlementNameResponse settlementNameKyivResponse() {
        return createSettlementName(settlementNameKyivRequest());
    }

    @Lazy
    @Bean(name = "settlementKyivRequest")
    public SettlementRequest settlementKyivRequest(){
        var type = typeSettlementVillageResponse();
        var name = settlementNameKyivResponse();

        return SettlementRequest
                .builder()
                .type(type.getId())
                .zipCode("43265")
                .name(name.getId())
                .build();
    }

    @Lazy
    @Bean(name = "settlementRivneRequest")
    public SettlementRequest settlementRivneRequest(){
        var type = createTypeSettlement(typeSettlementCityRequest());
        var name = createSettlementName(settlementNameRivneRequest());

        return SettlementRequest
                .builder()
                .type(type.getId())
                .zipCode("12345")
                .name(name.getId())
                .build();
    }

    @Lazy
    @Bean(name = "nameRivneRequest")
    public SettlementNameRequest settlementNameRivneRequest(){
        return SettlementNameRequest
                .builder()
                .uaName("Рівне")
                .enName("Rivne")
                .build();
    }

    @Lazy
    @Bean(name = "nameKyivRequest")
    public SettlementNameRequest settlementNameKyivRequest(){
        return SettlementNameRequest
                .builder()
                .uaName("Київ")
                .enName("Kyiv")
                .build();
    }

    @Lazy
    @Bean(name = "typeSettlementCityRequest")
    public TypeSettlementRequest typeSettlementCityRequest(){
        return TypeSettlementRequest
                .builder()
                .uaName("місто")
                .enName("city")
                .build();
    }

    @Lazy
    @Bean(name = "typeSettlementVillageRequest")
    public TypeSettlementRequest typeSettlementVillageRequest(){
        return TypeSettlementRequest
                .builder()
                .uaName("село")
                .enName("village")
                .build();
    }

    @Lazy
    @Bean(name = "oblastRivneRequest")
    public OblastRequest oblastRivneRequest(){
        return OblastRequest
                .builder()
                .uaName("Рівненська")
                .enName("Rivnenska")
                .build();
    }

    @Lazy
    @Bean(name = "oblastKyivRequest")
    public OblastRequest oblastKyivRequest(){
        return OblastRequest
                .builder()
                .uaName("Київська")
                .enName("Kyivska")
                .build();
    }

    @Lazy
    @Bean(name = "districtRivneRequest")
    public DistrictRequest districtRivneRequest(){
        return DistrictRequest
                .builder()
                .uaName("Рівненський")
                .enName("Rivne")
                .build();
    }

    @Lazy
    @Bean(name = "districtBelotserkivskyiRequest")
    public DistrictRequest districtBelotserkivskyiRequest(){
        return DistrictRequest
                .builder()
                .uaName("Білоцерківський")
                .enName("Belotserkivskyi")
                .build();
    }

    private AddressResidenceResponse createAddress(AddressResidenceRequest request){
        return (AddressResidenceResponse) addressResidenceService.addValue(request);
    }

    private SettlementNameResponse createSettlementName(SettlementNameRequest request){
        return (SettlementNameResponse) nameService.addValue(request);
    }

    private TypeSettlementResponse createTypeSettlement(TypeSettlementRequest request){
        return (TypeSettlementResponse) typeService.addValue(request);
    }

    private SettlementResponse createSettlement(SettlementRequest request){
        return (SettlementResponse) settlementService.addValue(request);
    }
}
