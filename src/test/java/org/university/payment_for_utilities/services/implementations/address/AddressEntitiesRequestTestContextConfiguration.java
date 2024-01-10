package org.university.payment_for_utilities.services.implementations.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.university.payment_for_utilities.configurations.DataBaseConfiguration;
import org.university.payment_for_utilities.pojo.requests.address.*;

@TestConfiguration
@ComponentScan(basePackages = "org.university.payment_for_utilities.services.implementations.address")
@Import(DataBaseConfiguration.class)
public class AddressEntitiesRequestTestContextConfiguration {
    @Autowired
    private TypeSettlementServiceImpl typeService;
    @Autowired
    private SettlementNameServiceImpl nameService;
    @Autowired
    private SettlementServiceImpl settlementService;

    @Lazy
    @Bean(name = "addressResidenceRequest")
    public AddressResidenceRequest addressResidenceRequest(){
        var settlementResponse = settlementService.addValue(settlementRequest());
        var settlement = settlementService.createEntity(settlementResponse);

        return AddressResidenceRequest
                .builder()
                .settlement(settlement)
                .uaNameStreet("вулиця")
                .enNameStreet("street")
                .numHouse("5a")
                .numEntrance("1")
                .numApartment("305")
                .build();
    }

    @Lazy
    @Bean(name = "settlementRequest")
    public SettlementRequest settlementRequest(){
        var typeResponse = typeService.addValue(typeSettlementCityRequest());
        var type = typeService.createEntity(typeResponse);

        var nameResponse = nameService.addValue(settlementNameRivneRequest());
        var name = nameService.createEntity(nameResponse);

        return SettlementRequest
                .builder()
                .type(type)
                .zipCode("12345")
                .name(name)
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
}
