-- V9: create improvements table

CREATE TABLE IF NOT EXISTS improvements (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    company_id uuid NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    original_content text NOT NULL,
    improved_content text NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_improvements_company_id ON improvements(company_id);
