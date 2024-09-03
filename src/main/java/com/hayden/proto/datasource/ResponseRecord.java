package com.hayden.proto.datasource;

import com.hayden.proto.datasource_proto.cardinality.Any;
import com.hayden.proto.proto.Prototype;
import com.hayden.proto.ty.CompositePrototyped;

public interface ResponseRecord extends CompositePrototyped<DataSourceClient.DataRecordResponseContract> {

    default Any<? extends Prototype> contracts() {
        var p = proto().responseContracts().pluralize();
        var k = proto().wire();

//        var out = new Prototype[p.length + k];
        // TODO:
        return null;
    }

}
