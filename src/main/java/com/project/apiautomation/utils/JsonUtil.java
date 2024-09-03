package com.project.apiautomation.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.apiautomation.config.ER;
import com.project.apiautomation.config.JsonReader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonUtil {

    public static void getJsonDifferences(String expectedJsonResponse, String actualJsonResponse) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse the JSON strings into JsonNode objects
        JsonNode tree1 = objectMapper.readTree(expectedJsonResponse);
        JsonNode tree2 = objectMapper.readTree(actualJsonResponse);

        List<String> differences = new ArrayList<>();
        compareJsonNodes(tree1, tree2, differences, "");

        //return differences;
        if (differences.isEmpty()) {
            System.out.println("The JSON objects are equal.");
            //ER.Pass("Expected Json Response and Actual Json responses are equal. <br>");
            ER.Pass("Expected Json Response and Actual Json responses are equal. <br><br>Expected Json Response : <br> "+expectedJsonResponse+"<br>Actual Json Response : <br>"+actualJsonResponse);
        } else {
            System.out.println("Differences found:");
            //ER.Warning("Expected Json Response and Actual Json responses are not equal.");
            for (String difference : differences) {
                System.out.println(difference);
                ER.Warning("Expected Json Response and Actual Json responses are not equal.<br><br>"+difference);
            }
        }
    }

    private static void compareJsonNodes(JsonNode node1, JsonNode node2, List<String> differences, String path) {
        // Check if both nodes are objects
        if (node1.isObject() && node2.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields1 = node1.fields();
            while (fields1.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields1.next();
                String key = entry.getKey();
                JsonNode value1 = entry.getValue();
                JsonNode value2 = node2.get(key);

                if (value2 == null) {
                    differences.add("Missing key in second JSON: " + (path.isEmpty() ? key : path + "." + key));
                } else {
                    compareJsonNodes(value1, value2, differences, path.isEmpty() ? key : path + "." + key);
                }
            }

            // Check if node2 has any extra fields not present in node1
            Iterator<Map.Entry<String, JsonNode>> fields2 = node2.fields();
            while (fields2.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields2.next();
                String key = entry.getKey();
                if (!node1.has(key)) {
                    differences.add("Extra key in second JSON: " + (path.isEmpty() ? key : path + "." + key));
                }
            }

        // Check if both nodes are arrays
        } else if (node1.isArray() && node2.isArray()) {
            if (node1.size() != node2.size()) {
                differences.add("Array size difference at " + path + ": " + node1.size() + " vs " + node2.size());
            } else {
                for (int i = 0; i < node1.size(); i++) {
                    compareJsonNodes(node1.get(i), node2.get(i), differences, path + "[" + i + "]");
                }
            }

        // Check if both nodes are values and equal
        } else if (!node1.equals(node2)) {
            differences.add("Value difference at " + path + ": " + node1 + " vs " + node2);
        }
    }

//    public static void main(String[] args) {
//        String json1 = "{ \"name\": \"John\", \"age\": 30, \"address\": { \"city\": \"New York\", \"postalCode\": \"10001\" }, \"phoneNumbers\": [\"123-456-7890\", \"987-654-3210\"] }";
//        String json2 = "{ \"name\": \"John\", \"age\": 30, \"address\": { \"city\": \"Los Angeles\", \"postalCode\": \"90001\" }, \"phoneNumbers\": [\"123-456-7890\", \"987-654-3211\"] }";
//
//        try {
//            List<String> differences = getJsonDifferences(json1, json2);
//            if (differences.isEmpty()) {
//                System.out.println("The JSON objects are equal.");
//            } else {
//                System.out.println("Differences found:");
//                for (String difference : differences) {
//                    System.out.println(difference);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    
    /**
     * Updates a specified property in a nested JSON string with a new value.
     *
     * @param jsonString The JSON string to be updated.
     * @param propertyName The name of the property to update.
     * @param newValue The new value to set for the specified property.
     * @return The updated JSON string.
     */
    public static String updatePropertyInJsonString(String jsonString, String propertyName, String newValue) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Parse the JSON string into a JsonNode
            JsonNode rootNode = objectMapper.readTree(jsonString);

            // Update the property recursively
            updatePropertyRecursively(rootNode, propertyName, newValue);

            // Convert the updated JsonNode back to a JSON string
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);

        } catch (Exception e) {
            System.err.println("Error parsing or writing JSON: " + e.getMessage());
            return jsonString; // Return original JSON string if there's an error
        }
    }

    /**
     * Recursively updates a specified property in a JsonNode.
     *
     * @param node The current JsonNode being inspected.
     * @param propertyName The name of the property to update.
     * @param newValue The new value to set for the specified property.
     */
    private static void updatePropertyRecursively(JsonNode node, String propertyName, String newValue) {
        if (node instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) node;

            if (objectNode.has(propertyName)) {
                // Update the property if it exists
                objectNode.put(propertyName, newValue);
            }

            // Recursively check all child nodes
            for (JsonNode childNode : objectNode) {
                updatePropertyRecursively(childNode, propertyName, newValue);
            }
        } else if (node.isArray()) {
            // If the node is an array, check each element
            for (JsonNode arrayElement : node) {
                updatePropertyRecursively(arrayElement, propertyName, newValue);
            }
        }
    }
}
