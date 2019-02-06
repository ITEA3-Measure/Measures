package org.measure.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.measure.restmodel.RTMeasure;
import org.measure.restmodel.RTResponse;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class DirectMeasureImpl extends DirectMeasure {

    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        List<IMeasurement> result = new ArrayList<IMeasurement>();
        
        // Read Scope Properties
        String sonarQubeURL = getProperty("ServerURL");
        String login = getProperty("Login");
        String password = getProperty("Password");
        String component = getProperty("ProjectKey");
        String metricKeys = getProperty("Metrics");
        
        if(metricKeys == null || metricKeys.isEmpty()) {
            metricKeys = 
                    "complexity,duplicated_blocks,duplicated_files,duplicated_lines,duplicated_lines_density,new_violations,new_blocker_violations,new_critical_violations,new_info_violations,new_major_violations,new_minor_violations,violations,blocker_violations,critical_violations,info_violations,major_violations,minor_violations,false_positive_issues,open_issues,confirmed_issues,reopened_issues,code_smells,new_code_smells,sqale_rating,sqale_index,new_technical_debt,sqale_debt_ratio,new_sqale_debt_ratio,bugs,new_bugs,reliability_rating,reliability_remediation_effort,new_reliability_remediation_effort,vulnerabilities,new_vulnerabilities,security_rating,security_remediation_effort,new_security_remediation_effort,classes,comment_lines,comment_lines_density,directories,files,lines,ncloc,functions,projects,statements,branch_coverage,new_branch_coverage,conditions_by_line,covered_conditions_by_line,coverage,new_coverage,line_coverage,new_line_coverage,coverage_line_hits_data,lines_to_cover,new_lines_to_cover,skipped_tests,uncovered_conditions,new_uncovered_conditions,uncovered_lines,new_uncovered_lines,tests,test_execution_time,test_errors,test_failures,test_success_density,class_complexity,file_complexity,function_complexity,public_documented_api_density,public_undocumented_api,commented_out_code_lines,public_api,accessors,complexity_in_classes,complexity_in_functions,effort_to_reach_maintainability_rating_a,wont_fix_issues";
        }
        
        // Initialize RestTemplate
        RestTemplate restTemplate = new RestTemplate();
                
        // add message converter for null String
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.getObjectMapper().enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        restTemplate.getMessageConverters().add(0, messageConverter);
        
        // add basic auth (login, password)
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(login, password));
        
        
        // Measurement
        if(! sonarQubeURL.endsWith("/"))      
            sonarQubeURL = sonarQubeURL + "/";
        
        RTResponse response = restTemplate.getForObject(sonarQubeURL + "/api/measures/component?metricKeys=" + metricKeys + "&componentKey=" + component, RTResponse.class);
        
        
        DirectMeasureData data = new DirectMeasureData(new Date());
        for(RTMeasure rTmeasure : response.getComponent().getMeasures()) {
                String value;
                if(rTmeasure.getValue() != null) {
                    value = rTmeasure.getValue();
                } else {
                    // no values, only periods, mostly with metrics beginning with "new". Example: new_violations.
                    value = rTmeasure.getPeriods().get(1).getValue();
                }
                data.addValue(rTmeasure.getMetric(), value);
        }
        
        // Return data
        result.add(data);
        return result;
    }

}
