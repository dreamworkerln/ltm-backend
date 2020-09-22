CREATE DATABASE ltm;
CREATE USER ltmadmin WITH PASSWORD 'ltmadminpassword';
GRANT ALL PRIVILEGES ON DATABASE ltm TO ltmadmin;
\c ltm
CREATE SCHEMA ltm AUTHORIZATION ltmadmin;
SET search_path TO ltm;

