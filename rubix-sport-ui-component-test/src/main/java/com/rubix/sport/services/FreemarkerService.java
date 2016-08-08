package com.rubix.sport.services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * This component is used to read in freemarker templates directory within the resources.
 * <a href="http://freemarker.org/">Freemarker</a> has been selected because it allows data within the templates themselves to be executed.
 *
 */
@Component
public class FreemarkerService {

    /**
     * Base directory for freemarker templates
     */
    private static final String FREEMARKER_RESOURCE_DIR = "templates";
    private Configuration cfg;

    /**
     * Initialises the Freemarker templates and set the template directory
     */
    @PostConstruct
    public void init() {
        //Setup Freemarker
        cfg = new Configuration(Configuration.VERSION_2_3_24);
        cfg.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(), FREEMARKER_RESOURCE_DIR);
    }

    /**
     * Reads in a template, and merges in the supplied data model.
     * @param template
     *  Name of the file to retrieve.
     * @param dataObject
     *  Data object to merge in. Can be nul.
     * @return
     * ByteArrayOutputStream of the executed template.
     * @throws IOException
     *  Thrown when the template is not found.
     * @throws TemplateException
     *  Thrown when there is an issue with the freemarker template.
     */
    public ByteArrayOutputStream readTemplate(String template, Object dataObject) throws IOException, TemplateException {
        Template temp = this.cfg.getTemplate(template);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        temp.process(dataObject,writer);

        writer.close();
        return outputStream;
    }
}
