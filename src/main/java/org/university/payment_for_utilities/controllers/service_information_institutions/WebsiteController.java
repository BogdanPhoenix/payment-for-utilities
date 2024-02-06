package org.university.payment_for_utilities.controllers.service_information_institutions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.university.payment_for_utilities.pojo.requests.service_information_institutions.WebsiteRequest;
import org.university.payment_for_utilities.services.interfaces.service_information_institutions.WebsiteService;

@RestController
@RequestMapping("/${application.uri.main}/websites")
public class WebsiteController extends ServiceInfoBaseController<WebsiteRequest> {
    @Autowired
    public WebsiteController(WebsiteService service) {
        super(service);
    }
}
