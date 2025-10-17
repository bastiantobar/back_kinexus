-- Actualiza los paquetes
sudo apt update
-- Instala PostgreSQL
sudo apt install postgresql postgresql-contrib
-- Inicia el servicio de PostgreSQL
sudo service postgresql start
-- Estado
sudo service postgresql status

-- Entrar a la consola de PostgreSQL
sudo -i -u postgres
psql

CREATE DATABASE kinexus_db;
CREATE USER postgres WITH PASSWORD '1234';
ALTER ROLE postgres WITH SUPERUSER;
GRANT ALL PRIVILEGES ON DATABASE kinexus_db TO postgres;

ALTER USER postgres WITH PASSWORD '1234';
-- ASTA ACA 
-- Eliminar índices y tablas si existen (en orden de dependencias)
DROP INDEX IF EXISTS idx_users_email;
DROP TABLE IF EXISTS bloques_horario CASCADE;
DROP TABLE IF EXISTS sesiones CASCADE;
DROP TABLE IF EXISTS pagos CASCADE;
DROP TABLE IF EXISTS historiales CASCADE;
DROP TABLE IF EXISTS contenidos_medicos CASCADE;
DROP TABLE IF EXISTS fichas_medicas CASCADE;
DROP TABLE IF EXISTS citas CASCADE;
DROP TABLE IF EXISTS planes CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- Crear la tabla de usuarios (padre de casi todas)
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    tipo_usuario VARCHAR(20) CHECK (tipo_usuario IN ('admin', 'paciente', 'paciente_empresa')) NOT NULL,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_users_email ON users(email);

-- Crear la tabla de planes (no depende de otras)
CREATE TABLE planes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    asistencia INTEGER,
    numero_sesiones INTEGER,
    info_plan TEXT,
    evaluacion_inicial TEXT,
    evaluacion_final TEXT
);

-- Crear la tabla de fichas médicas (depende de users)
CREATE TABLE fichas_medicas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    paciente_id UUID NOT NULL,
    descripcion TEXT NOT NULL,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (paciente_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Crear la tabla de historiales (depende de users)
CREATE TABLE historiales (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    info_basica_paciente TEXT,
    paciente_id UUID NOT NULL,
    FOREIGN KEY (paciente_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Crear la tabla de citas (depende de users)
CREATE TABLE citas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    paciente_id UUID NULL, -- Puede ser NULL si aún no ha sido reservada
    admin_id UUID NOT NULL,
    fecha_hora TIMESTAMP NOT NULL,
    estado VARCHAR(20) CHECK (estado IN ('disponible', 'reservada', 'cancelada')) NOT NULL DEFAULT 'disponible',
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (paciente_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (admin_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Crear la tabla de contenidos médicos (depende de fichas_medicas)
CREATE TABLE contenidos_medicos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    ficha_id UUID NOT NULL,
    tipo_contenido VARCHAR(10) CHECK (tipo_contenido IN ('pdf', 'video')) NOT NULL,
    url TEXT NOT NULL,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ficha_id) REFERENCES fichas_medicas(id) ON DELETE CASCADE
);

-- Crear la tabla de sesiones (depende de planes)
CREATE TABLE sesiones (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    fecha_hora TIMESTAMP NOT NULL,
    asistencia BOOLEAN,
    cancelado BOOLEAN,
    evolucion_clinica TEXT,
    plan_id UUID NOT NULL,
    FOREIGN KEY (plan_id) REFERENCES planes(id) ON DELETE CASCADE
);

-- Crear la tabla de pagos (depende de users y planes)
CREATE TABLE pagos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID NOT NULL,
    monto DECIMAL(10,2) NOT NULL CHECK (monto >= 0),
    metodo_pago VARCHAR(20) CHECK (metodo_pago IN ('efectivo', 'tarjeta', 'transferencia')) NOT NULL,
    estado_pago VARCHAR(20) CHECK (estado_pago IN ('pendiente', 'completado', 'fallido')) NOT NULL DEFAULT 'pendiente',
    fecha_pago TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    plan_id UUID NULL,
    FOREIGN KEY (usuario_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (plan_id) REFERENCES planes(id) ON DELETE SET NULL
);

-- Crear la tabla de bloques_horario (no depende de otras)
CREATE TABLE bloques_horario (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    descripcion TEXT NOT NULL
);

-- EMPRESAS
CREATE TABLE empresas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(255) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    persona_a_cargo VARCHAR(255) NOT NULL,
    telefono VARCHAR(30),
    email VARCHAR(255) NOT NULL,
    direccion_casa_matriz VARCHAR(255),
    descripcion TEXT
);

-- PLANES_EMPRESA
CREATE TABLE planes_empresa (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    empresa_id UUID NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    fecha_inicio TIMESTAMP NOT NULL,
    fecha_termino TIMESTAMP,
    valor DECIMAL(10,2) NOT NULL,
    numero_sesiones INTEGER,
    FOREIGN KEY (empresa_id) REFERENCES empresas(id) ON DELETE CASCADE
);

-- SUCURSALES
CREATE TABLE sucursales (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    empresa_id UUID NOT NULL,
    numero_trabajadores INTEGER,
    email VARCHAR(255),
    telefono VARCHAR(30),
    direccion VARCHAR(255),
    FOREIGN KEY (empresa_id) REFERENCES empresas(id) ON DELETE CASCADE
);

-- USUARIOS_EMPRESA
CREATE TABLE usuarios_empresa (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sucursal_id UUID NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    edad INTEGER,
    genero VARCHAR(20),
    fecha_nacimiento DATE,
    cargo VARCHAR(100),
    discapacidad VARCHAR(100),
    FOREIGN KEY (sucursal_id) REFERENCES sucursales(id) ON DELETE CASCADE
);

-- SESIONES_EMPRESA
CREATE TABLE sesiones_empresa (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    plan_id UUID NOT NULL,
    sucursal_id UUID NOT NULL,
    fecha_hora TIMESTAMP NOT NULL,
    estado VARCHAR(20) CHECK (estado IN ('por_realizar', 'finalizada', 'cancelada')) NOT NULL,
    descripcion_clinica TEXT,
    asistencia NUMERIC(5,2), -- porcentaje de asistencia
    FOREIGN KEY (plan_id) REFERENCES planes_empresa(id) ON DELETE CASCADE,
    FOREIGN KEY (sucursal_id) REFERENCES sucursales(id) ON DELETE CASCADE
);

-- SESION_TRABAJADOR (asistencia de cada trabajador a cada sesión)
CREATE TABLE sesion_trabajador (
    sesion_id UUID NOT NULL,
    usuario_empresa_id UUID NOT NULL,
    asistencia BOOLEAN,
    PRIMARY KEY (sesion_id, usuario_empresa_id),
    FOREIGN KEY (sesion_id) REFERENCES sesiones_empresa(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_empresa_id) REFERENCES usuarios_empresa(id) ON DELETE CASCADE
);

-- PAGOS_EMPRESA
CREATE TABLE pagos_empresa (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    empresa_id UUID NOT NULL,
    plan_id UUID NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    metodo_pago VARCHAR(20) CHECK (metodo_pago IN ('efectivo', 'tarjeta', 'transferencia')) NOT NULL,
    estado_pago VARCHAR(20) CHECK (estado_pago IN ('pendiente', 'completado', 'fallido')) NOT NULL DEFAULT 'pendiente',
    fecha_pago TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (empresa_id) REFERENCES empresas(id) ON DELETE CASCADE,
    FOREIGN KEY (plan_id) REFERENCES planes_empresa(id) ON DELETE CASCADE
);

-- HISTORIALES_EMPRESA (puedes extender con más detalles según lo que quieras auditar)
CREATE TABLE historiales_empresa (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    empresa_id UUID NOT NULL,
    tipo VARCHAR(30) NOT NULL, -- 'plan', 'sesion', 'pago', etc.
    descripcion TEXT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (empresa_id) REFERENCES empresas(id) ON DELETE CASCADE
);