-- Enums for MySQL
CREATE TABLE checklist_status (
    status ENUM('PASS', 'FAIL') PRIMARY KEY
);

CREATE TABLE equipment_status (
    status VARCHAR(50) PRIMARY KEY
);

CREATE TABLE maintenance_type (
    type VARCHAR(50) PRIMARY KEY
);

CREATE TABLE maintenance_status (
    status VARCHAR(50) PRIMARY KEY
);

CREATE TABLE user_status (
    status VARCHAR(50) PRIMARY KEY
);

CREATE TABLE shift_status (
    status VARCHAR(50) PRIMARY KEY
);

-- Core tables
CREATE TABLE equipment_category (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    category VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE role (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE certificate (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    certificate_name VARCHAR(255) NOT NULL,
    certificate_number VARCHAR(100) UNIQUE NOT NULL,
    issue_date DATE NOT NULL,
    expiry_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_certificate_expiry (expiry_date)
);

CREATE TABLE user (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    status VARCHAR(50) DEFAULT 'ACTIVE',
    hire_date DATE,
    last_login TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (status) REFERENCES user_status(status),
    INDEX idx_user_email (email),
    INDEX idx_user_status (status)
);

CREATE TABLE equipment (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    mileage INT DEFAULT 0,
    date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modified TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    next_maintenance_hours INT DEFAULT 0,
    status VARCHAR(50) DEFAULT 'AVAILABLE',
    category_id CHAR(36) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (status) REFERENCES equipment_status(status),
    FOREIGN KEY (category_id) REFERENCES equipment_category(id),
    INDEX idx_equipment_status (status),
    INDEX idx_equipment_category (category_id),
    INDEX idx_equipment_maintenance (next_maintenance_hours)
);

CREATE TABLE shift (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    shift_date DATE NOT NULL,
    supervisor_id CHAR(36) NOT NULL,
    target_production DECIMAL(10,2),
    status VARCHAR(50) DEFAULT 'SCHEDULED',
    handover_notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (supervisor_id) REFERENCES user(id),
    FOREIGN KEY (status) REFERENCES shift_status(status),
    INDEX idx_shift_date (shift_date),
    INDEX idx_shift_supervisor (supervisor_id)
);

-- Relationship tables
CREATE TABLE user_role (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    user_id CHAR(36) NOT NULL,
    role_id CHAR(36) NOT NULL,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    assigned_by VARCHAR(100),
    expires_at TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_role (user_id, role_id),
    INDEX idx_user_role_user (user_id),
    INDEX idx_user_role_expires (expires_at)
);

CREATE TABLE user_certificate (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    user_id CHAR(36) NOT NULL,
    certificate_id CHAR(36) NOT NULL,
    comments TEXT,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (certificate_id) REFERENCES certificate(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_certificate (user_id, certificate_id),
    INDEX idx_user_cert_user (user_id),
    INDEX idx_user_cert_certificate (certificate_id)
);

-- Operational tables
CREATE TABLE maintenance (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    equipment_id CHAR(36) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    type VARCHAR(50) NOT NULL,
    status VARCHAR(50) DEFAULT 'SCHEDULED',
    start_time TIMESTAMP NULL,
    end_time TIMESTAMP NULL,
    downtime_hours DECIMAL(5,2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (equipment_id) REFERENCES equipment(id) ON DELETE CASCADE,
    FOREIGN KEY (type) REFERENCES maintenance_type(type),
    FOREIGN KEY (status) REFERENCES maintenance_status(status),
    INDEX idx_maintenance_equipment (equipment_id),
    INDEX idx_maintenance_status (status),
    INDEX idx_maintenance_dates (start_time, end_time)
);

CREATE TABLE pre_start_check (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    equipment_id CHAR(36) NOT NULL,
    shift_id BIGINT NOT NULL,
    username VARCHAR(100) NOT NULL,
    oil_level BOOLEAN DEFAULT FALSE,
    engine BOOLEAN DEFAULT FALSE,
    mileage INT NOT NULL,
    defects_reported TEXT,
    overall_status ENUM('PASS', 'FAIL') NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (equipment_id) REFERENCES equipment(id) ON DELETE CASCADE,
    FOREIGN KEY (shift_id) REFERENCES shift(id) ON DELETE CASCADE,
    FOREIGN KEY (overall_status) REFERENCES checklist_status(status),
    INDEX idx_prestart_equipment (equipment_id),
    INDEX idx_prestart_shift (shift_id),
    INDEX idx_prestart_date (created_at)
);

CREATE TABLE compliance (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    user_certificate_id CHAR(36) NULL,
    pre_start_check_id CHAR(36) NULL,
    compliant BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_certificate_id) REFERENCES user_certificate(id) ON DELETE SET NULL,
    FOREIGN KEY (pre_start_check_id) REFERENCES pre_start_check(id) ON DELETE SET NULL,
    CHECK (user_certificate_id IS NOT NULL OR pre_start_check_id IS NOT NULL),
    INDEX idx_compliance_certificate (user_certificate_id),
    INDEX idx_compliance_check (pre_start_check_id),
    INDEX idx_compliance_status (compliant)
);

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