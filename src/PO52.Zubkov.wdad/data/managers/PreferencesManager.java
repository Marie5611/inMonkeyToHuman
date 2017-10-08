package PO52.Zubkov.wdad.data.managers;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class PreferencesManager {
    private static PreferencesManager instance;
        private Document doc;
        private static final String FILE_PATH="src\\PO52\\Zubkov\\wdad\\resources\\configuration\\appconfig.xml";

                private PreferencesManager() throws Exception {
               DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setValidating(false);
                DocumentBuilder builder = factory.newDocumentBuilder();
               doc = builder.parse(new File(FILE_PATH));
            }

                public static PreferencesManager getInstance() throws Exception {
               if (instance == null)
                        instance = new PreferencesManager();
                return instance;
           }

                private Element getElement(String nameField) {
        NodeList nodeList = doc.getElementsByTagName(nameField);
               Element element = (Element) nodeList.item(0);
              return element;
            }

                public String getCreateregistry() {
                return getElement("createregistry").getTextContent();
            }

                public void setCreateregistry(String value) {
                getElement("createregistry").setTextContent(value);
            }

                public String getRegistryaddress() {
               return getElement("registryaddress").getTextContent();
           }

                public void setRegistryaddress(String value) {
                getElement("registryaddress").setTextContent(value);
            }

                public int getRegistryport() {
               return Integer.parseInt(getElement("registryport").getTextContent());
            }

                public void setRegistryport(int value) {
                getElement("registryport").setTextContent(String.valueOf(value));
           }

               public String getPolicypath() {
                return getElement("policypath").getTextContent();
            }

                public void setPolicypath(String value) {
                getElement("policypath").setTextContent(value);
           }

                public String getUsecodebaseonly() {
                return getElement("usecodebaseonly").getTextContent();
            }

                public void setUsecodebaseonly(String value) {
                getElement("usecodebaseonly").setTextContent(value);
           }

                public String getClassprovider() {
                return getElement("classprovider").getTextContent();
            }

                public void setClassprovider(String value) {
                getElement("classprovider").setTextContent(value);
            }

                public void saveTransformXml()

        {
                try {
                        Transformer transformer = TransformerFactory.newInstance()
                                        .newTransformer();
                       DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(new File(FILE_PATH));
                        transformer.transform(source, result);
                   } catch (TransformerConfigurationException ex) {
                        System.out.println(ex.getMessage());
                    } catch (TransformerException ex) {
                        System.out.println(ex.getMessage());
                    }
            }
    }

