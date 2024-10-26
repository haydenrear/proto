package com.hayden.proto.prototyped.sources.server.requestresponse;


import com.hayden.proto.prototype.datasource.data.inputs.request.PathContractProto;
import com.hayden.proto.prototype.datasource.server.ServerEndpointContract;
import com.hayden.proto.prototyped.sources.data.inputs.request.Path;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Inherited;

@Inherited
@Controller
@Path
//@RequestMapping
public @interface ComTroller {

//    @AliasFor(annotation = Controller.class,
//              attribute = "value")
//    String controller() default "";
//
//    @AliasFor(annotation = RestController.class,
//              attribute = "value")
//    String restController() default "";
//
//    @AliasFor(annotation = RequestMapping.class,
//              attribute = "path")
//    String[] requestPath() default {};

    @AliasFor(annotation = Path.class,
              attribute = "proto")
    Class<? extends PathContractProto>[] pathContract() default {};


    Class<? extends ServerEndpointContract>[] proto() default {};


}
