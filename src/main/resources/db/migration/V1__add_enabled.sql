-- Flyway migration V1: Add 'enabled' column to products (idempotent)
-- Date: 2025-11-17
-- This script is safe to run multiple times: it uses IF NOT EXISTS and sets default/not null

BEGIN;

-- Add column if it doesn't exist
ALTER TABLE products ADD COLUMN IF NOT EXISTS enabled BOOLEAN;

-- Set TRUE for existing rows that are NULL
UPDATE products SET enabled = TRUE WHERE enabled IS NULL;

-- Set default and NOT NULL
ALTER TABLE products ALTER COLUMN enabled SET DEFAULT TRUE;
ALTER TABLE products ALTER COLUMN enabled SET NOT NULL;

COMMIT;
