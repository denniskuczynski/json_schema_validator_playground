package json_schema_validator_playground;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.report.ProcessingMessage;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.github.fge.jsonschema.util.JsonLoader;

import java.io.IOException;

public class Test {

    public static void main(String[] args) {
        try {
            final JsonNode fstabSchema = loadResource("/schema.json");
            final JsonNode pass = loadResource("/pass.json");
            final JsonNode fail = loadResource("/fail.json");

            final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
            final JsonSchema schema = factory.getJsonSchema(fstabSchema);

            ProcessingReport report;

            report = schema.validate(pass);
            printReport(report);

            report = schema.validate(fail);
            printReport(report);
        } catch(Throwable t) {
            System.out.println("Exception in Test");
            t.printStackTrace();
        }
    }

    protected static void printReport(final ProcessingReport report)
    {
        final boolean success = report.isSuccess();
        System.out.println("Validation " + (success ? "succeeded" : "failed"));

        if (!success) {
            System.out.println("---- BEGIN REPORT ----");
            for (final ProcessingMessage message : report)
                System.out.println(message);
            System.out.println("---- END REPORT ----");
        }
    }

    protected static JsonNode loadResource(final String name)
        throws IOException
    {
        return JsonLoader.fromResource("/json_schema_validator_playground" + name);
    }
}
