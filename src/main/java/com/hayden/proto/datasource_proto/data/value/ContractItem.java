package com.hayden.proto.datasource_proto.data.value;

import com.hayden.proto.proto.Prototype;

public interface ContractItem extends Prototype {

    record MaxLength() implements ContractItem {}

    record Nullable() implements ContractItem {}

}
