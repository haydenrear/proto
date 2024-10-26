package com.hayden.proto.prototyped.datasources.ai.modelserver.client;

import com.hayden.proto.prototype.cardinality.Any;
import com.hayden.proto.prototype.cardinality.Plural;
import com.hayden.proto.prototype.datasource.client.DataSourceClientContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.RequestConstructContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.RequestContractProto;
import com.hayden.proto.prototype.datasource.data.response.ResponseConstructContractProto;
import com.hayden.proto.prototype.datasource.data.response.ResponseContractProto;
import com.hayden.proto.prototype.datasource.exec.ExecContractProto;
import com.hayden.proto.prototype.datasource.data.inputs.request.ai_request.AiRequestConstructProto;
import com.hayden.proto.prototype.factory.ModelServerPropertySource;
import com.hayden.proto.prototype.factory.persistdescript.ProgramDescriptorTarget;
import com.hayden.proto.prototyped.datasources.ai.modelserver.data.ModelServerRecordProto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Inherited;
import java.util.Objects;

@NoArgsConstructor
public class ModelServerContractProto implements DataSourceClientContractProto {


    private ModelServerRequestContract requestConstructs;

    private ModelServerResponseContract response;

    @Getter
    @Setter
    private ProgramDescriptorTarget target;


    public void set(
            ModelServerPropertySource contextLength,
            ModelServerRecordProto response
    ) {
        this.requestConstructs =  new ModelServerRequestContract(contextLength.getContextLength(), contextLength.getModelLocation(), response);
        this.response =  new ModelServerResponseContract();
    }

    @Override
    public RequestContractProto requestContracts() {
        return () -> requestConstructs.requestContracts();
    }

    @Override
    public ResponseContractProto responseContracts() {
        return () -> response.responseContracts();
    }

    @Override
    public ExecContractProto exec() {
        return () -> () -> new ExecContractProto.ExecutionDescriptor[0];
    }

    public ModelServerRequestContract requestConstructs() {
        return requestConstructs;
    }

    public ModelServerResponseContract response() {
        return response;
    }

    @Override
    public void wirePrototype(ApplicationContext ctx) {
        var propertySource = ctx.getBean(ModelServerPropertySource.class);
        this.set(propertySource, new ModelServerRecordProto());
    }

}
