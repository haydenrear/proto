package com.hayden.proto.datasource_proto.exec;

import com.hayden.proto.datasource_proto.cardinality.All;
import com.hayden.proto.datasource_proto.data.value.ClassValueContractProto;
import com.hayden.proto.permitting.Permitting;
import com.hayden.proto.permitting.Value;
import com.hayden.proto.proto.CompositePrototype;
import com.hayden.proto.proto.Prototype;

public interface ExecContractProto extends CompositePrototype<ExecContractProto.ExecutionDescriptor> {

    sealed interface ExecutionDescriptor extends Prototype
        permits
            ExecutionDescriptor.InputType,
            ExecutionDescriptor.OutputType,
            ExecutionDescriptor.MethodName {

        record InputType<T>(ClassValueContractProto permitting, Class<T> value)
                implements ExecutionDescriptor, Permitting<ClassValueContractProto>, Value<Class<T>, InputType> {}

        record OutputType<T>(ClassValueContractProto permitting, Class<T> value)
                implements ExecutionDescriptor, Permitting<ClassValueContractProto>, Value<Class<T>, InputType> {}

        record MethodName(ClassValueContractProto permitting, String value)
                implements ExecutionDescriptor, Permitting<ClassValueContractProto>, Value<String, InputType> {}

    }

    @Override
    All<ExecutionDescriptor> prototypes();

}
