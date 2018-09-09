package org.sandbox.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.sandbox.config.Messages;
import org.sandbox.json.ClientTypeDeserializer;
import org.sandbox.json.ReferenceSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "client")
@ApiObject(name = "Client", description = Messages.DESCRIPTION_CLIENT)
public class Client extends BaseEntityCustomId {

    @Column(name = "client_name")
    @JsonProperty
    @ApiObjectField(description = Messages.DESCRIPTION_CLIENT_NAME)
    private String clientName;

    @Column(name = "client_type")
    @Type(type = "org.sandbox.hibernate.ClientTypeType")
    @JsonProperty
    @JsonSerialize(using = ReferenceSerializer.class)
    @JsonDeserialize(using = ClientTypeDeserializer.class)
    @ApiObjectField(description = Messages.DESCRIPTION_CLIENT_TYPE)
    private ClientType clientType;

    @Column(name = "inn")
    @JsonProperty
    @ApiObjectField(description = Messages.DESCRIPTION_INN)
    private String inn;

    @Column(name = "kpp")
    @JsonProperty
    @ApiObjectField(description = Messages.DESCRIPTION_KPP)
    private String kpp;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }
}
