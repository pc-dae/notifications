package mn.dae.pc.notifications.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class Email {
    @JsonProperty("recipient")
    private String recipient;

    @JsonProperty("data")
    private Map<String, String> data;

    @JsonProperty("templateId")
    private String templateId;

    @JsonProperty("template")
    private String template;

    public Email(String recipient, Map<String, String> data, String templateId, String template) {
        this.recipient = recipient;
        this.data = data;
        this.templateId = templateId;
        this.template = template;
    }

    @Override
    public String toString() {
        return "Email{" +
                "to=" + recipient +
                ", data='" + data + '\'' +
                ", template=" + templateId +
                '}';
    }
}
