package org.measure.impl;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.services.Measure;
import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceQuery;

@objid ("234ebfd9-55e8-46f2-a046-7169affce83a")
public class DirectMeasureImpl extends DirectMeasure {
    @objid ("b8542407-178a-4357-a272-764d365117a2")
    @Override
    public List<IMeasurement> getMeasurement() throws Exception {
        String serverURL = getProperty("ServerURL");
        String login = getProperty("Login");
        String password = getProperty("Password");
        String projectKey = getProperty("ProjectKey");
        
        try {
        
            Sonar sonar = Sonar.create(serverURL, login, password);
            List<IMeasurement> result = new ArrayList<IMeasurement>();
            DirectMeasureData data = new DirectMeasureData();
            result.add(data);
        
            if (sonar != null) {
                collectSonarCubeMeasure(projectKey, sonar, data, "complexity");
                collectSonarCubeMeasure(projectKey, sonar, data, "cognitive_complexity");
                collectSonarCubeMeasure(projectKey, sonar, data, "class_complexity");
                collectSonarCubeMeasure(projectKey, sonar, data, "file_complexity");
                collectSonarCubeMeasure(projectKey, sonar, data, "function_complexity");
                collectSonarCubeMeasure(projectKey, sonar, data, "comment_lines");
                collectSonarCubeMeasure(projectKey, sonar, data, "comment_lines_density");
                collectSonarCubeMeasure(projectKey, sonar, data, "public_documented_api_density");
                collectSonarCubeMeasure(projectKey, sonar, data, "public_undocumented_api");
                collectSonarCubeMeasure(projectKey, sonar, data, "commented_out_code_lines");
                collectSonarCubeMeasure(projectKey, sonar, data, "duplicated_files");
                collectSonarCubeMeasure(projectKey, sonar, data, "duplicated_lines");
                collectSonarCubeMeasure(projectKey, sonar, data, "duplicated_lines_density");
                collectSonarCubeMeasure(projectKey, sonar, data, "new_violations");
                collectSonarCubeMeasure(projectKey, sonar, data, "new_blocker_violations");
                collectSonarCubeMeasure(projectKey, sonar, data, "new_critical_violations");
                collectSonarCubeMeasure(projectKey, sonar, data, "new_major_violations");
                collectSonarCubeMeasure(projectKey, sonar, data, "new_minor_violations");
                collectSonarCubeMeasure(projectKey, sonar, data, "new_info_violations");
                collectSonarCubeMeasure(projectKey, sonar, data, "violations");
                collectSonarCubeMeasure(projectKey, sonar, data, "blocker_violations");
                collectSonarCubeMeasure(projectKey, sonar, data, "critical_violations");
                collectSonarCubeMeasure(projectKey, sonar, data, "major_violations");
                collectSonarCubeMeasure(projectKey, sonar, data, "minor_violations");
                collectSonarCubeMeasure(projectKey, sonar, data, "info_violations");
                collectSonarCubeMeasure(projectKey, sonar, data, "false_positive_issues");
                collectSonarCubeMeasure(projectKey, sonar, data, "open_issues");
                collectSonarCubeMeasure(projectKey, sonar, data, "confirmed_issues");
                collectSonarCubeMeasure(projectKey, sonar, data, "reopened_issues");
                collectSonarCubeMeasure(projectKey, sonar, data, "code_smells");
                collectSonarCubeMeasure(projectKey, sonar, data, "new_code_smells");
                collectSonarCubeMeasure(projectKey, sonar, data, "sqale_rating");
                collectSonarCubeMeasure(projectKey, sonar, data, "sqale_index");
                collectSonarCubeMeasure(projectKey, sonar, data, "new_technical_debt");
                collectSonarCubeMeasure(projectKey, sonar, data, "sqale_debt_ratio");
                collectSonarCubeMeasure(projectKey, sonar, data, "new_sqale_debt_ratio");
                collectSonarCubeMeasure(projectKey, sonar, data, "alert_status");
                collectSonarCubeMeasure(projectKey, sonar, data, "quality_gate_details");
                collectSonarCubeMeasure(projectKey, sonar, data, "bugs");
                collectSonarCubeMeasure(projectKey, sonar, data, "new_bugs");
                collectSonarCubeMeasure(projectKey, sonar, data, "reliability_rating");
                collectSonarCubeMeasure(projectKey, sonar, data, "reliability_remediation_effort");
                collectSonarCubeMeasure(projectKey, sonar, data, "new_reliability_remediation_effort");
                collectSonarCubeMeasure(projectKey, sonar, data, "vulnerabilities");
                collectSonarCubeMeasure(projectKey, sonar, data, "new_vulnerabilities");
                collectSonarCubeMeasure(projectKey, sonar, data, "security_rating");
                collectSonarCubeMeasure(projectKey, sonar, data, "security_remediation_effort");
                collectSonarCubeMeasure(projectKey, sonar, data, "new_security_remediation_effort");
                collectSonarCubeMeasure(projectKey, sonar, data, "classes");
                collectSonarCubeMeasure(projectKey, sonar, data, "directories");
                collectSonarCubeMeasure(projectKey, sonar, data, "files");
                collectSonarCubeMeasure(projectKey, sonar, data, "lines");
                collectSonarCubeMeasure(projectKey, sonar, data, "ncloc");
                collectSonarCubeMeasure(projectKey, sonar, data, "ncloc_language_distribution");
                collectSonarCubeMeasure(projectKey, sonar, data, "functions");
                collectSonarCubeMeasure(projectKey, sonar, data, "projects");
                collectSonarCubeMeasure(projectKey, sonar, data, "public_api");
                collectSonarCubeMeasure(projectKey, sonar, data, "statements");
                collectSonarCubeMeasure(projectKey, sonar, data, "branch_coverage");
                collectSonarCubeMeasure(projectKey, sonar, data, "new_branch_coverage");
                collectSonarCubeMeasure(projectKey, sonar, data, "branch_coverage_hits_data");
                collectSonarCubeMeasure(projectKey, sonar, data, "conditions_by_line");
                collectSonarCubeMeasure(projectKey, sonar, data, "covered_conditions_by_line");
                collectSonarCubeMeasure(projectKey, sonar, data, "coverage");
                collectSonarCubeMeasure(projectKey, sonar, data, "new_coverage");
                collectSonarCubeMeasure(projectKey, sonar, data, "line_coverage");
                collectSonarCubeMeasure(projectKey, sonar, data, "new_line_coverage");
                collectSonarCubeMeasure(projectKey, sonar, data, "coverage_line_hits_data");
                collectSonarCubeMeasure(projectKey, sonar, data, "lines_to_cover");
                collectSonarCubeMeasure(projectKey, sonar, data, "new_lines_to_cover");
                collectSonarCubeMeasure(projectKey, sonar, data, "skipped_tests");
                collectSonarCubeMeasure(projectKey, sonar, data, "uncovered_conditions");
                collectSonarCubeMeasure(projectKey, sonar, data, "new_uncovered_conditions");
                collectSonarCubeMeasure(projectKey, sonar, data, "uncovered_lines");
                collectSonarCubeMeasure(projectKey, sonar, data, "new_uncovered_lines");
                collectSonarCubeMeasure(projectKey, sonar, data, "tests");
                collectSonarCubeMeasure(projectKey, sonar, data, "test_execution_time");
                collectSonarCubeMeasure(projectKey, sonar, data, "test_errors");
                collectSonarCubeMeasure(projectKey, sonar, data, "test_failures");
                collectSonarCubeMeasure(projectKey, sonar, data, "test_success_density");
        
            } else {
                throw new Exception("Connection to SonarCube  Server Failled");
            }
        
            return result;
        } catch (Exception e) {
            throw new Exception("Error during Measure Execution : " + e.getMessage(), e);
        }
    }

    @objid ("bc3e4c0f-7964-426a-b219-0f56cf2db5b3")
    private void collectSonarCubeMeasure(String projectKey, Sonar sonar, DirectMeasureData data, String type) {
        Resource struts = sonar.find(ResourceQuery.createForMetrics(projectKey, type));
        if (struts != null) {
            Measure m = struts.getMeasure(type);
            if (m != null) {
                data.addValue(type, m.getValue());
            } else {
                data.addValue(type, new Double(0));
            }
        } else {
            data.addValue(type, new Double(0));
        }
    }

}
