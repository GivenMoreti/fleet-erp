-- Insert default enum values
INSERT INTO checklist_status (status) VALUES ('PASS'), ('FAIL');
INSERT INTO equipment_status (status) VALUES ('AVAILABLE'), ('IN_USE'), ('UNDER_MAINTENANCE'), ('OUT_OF_SERVICE');
INSERT INTO maintenance_type (type) VALUES ('PREVENTIVE'), ('CORRECTIVE'), ('PREDICTIVE'), ('EMERGENCY');
INSERT INTO maintenance_status (status) VALUES ('SCHEDULED'), ('IN_PROGRESS'), ('COMPLETED'), ('CANCELLED');
INSERT INTO user_status (status) VALUES ('ACTIVE'), ('INACTIVE'), ('SUSPENDED'), ('TERMINATED');
INSERT INTO shift_status (status) VALUES ('SCHEDULED'), ('IN_PROGRESS'), ('COMPLETED'), ('CANCELLED');

-- Insert default roles
INSERT INTO role (id, name, description) VALUES 
(UUID(), 'ADMIN', 'System administrator with full access'),
(UUID(), 'SUPERVISOR', 'Shift supervisor and team manager'),
(UUID(), 'OPERATOR', 'Equipment operator'),
(UUID(), 'MECHANIC', 'Maintenance technician');

-- Insert default equipment categories
INSERT INTO equipment_category (id, category) VALUES 
(UUID(), 'HAUL_TRUCK'),
(UUID(), 'EXCAVATOR'),
(UUID(), 'LOADER'),
(UUID(), 'DRILL_RIG'),
(UUID(), 'BULLDOZER'),
(UUID(), 'GRADER');