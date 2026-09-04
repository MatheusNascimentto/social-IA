-- V5: create hashtags table

CREATE TABLE IF NOT EXISTS hashtags (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    company_id uuid NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    tags text NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_hashtags_company_id ON hashtags(company_id);
