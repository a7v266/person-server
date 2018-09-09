package org.sandbox.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.sandbox.config.Messages;
import org.sandbox.json.LocalDateDeserializer;
import org.sandbox.json.LocalDateSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "contract")
@ApiObject(name = "Contract", description = Messages.DESCRIPTION_CONTRACT)
public class Contract extends BaseEntityCustomId {

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonProperty
    @ApiObjectField(description = Messages.DESCRIPTION_CLIENT)
    private Client client;

    @Column(name = "contract_number")
    @JsonProperty
    @ApiObjectField(description = Messages.DESCRIPTION_CONTRACT_NUMBER)
    private String contractNumber;

    @Column(name = "contract_date")
    @NotNull(message = Messages.ERROR_CONTRACT_DATE_EMPTY)
    @JsonProperty
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @ApiObjectField(description = Messages.DESCRIPTION_CONTRACT_DATE)
    private LocalDate contractDate;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public LocalDate getContractDate() {
        return contractDate;
    }

    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }
}
