-- V7: create reel_scripts table

CREATE TABLE IF NOT EXISTS reel_scripts (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    company_id uuid NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    script text NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_reel_scripts_company_id ON reel_scripts(company_id);
