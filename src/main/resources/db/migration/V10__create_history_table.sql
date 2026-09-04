-- V10: create generated_history table

CREATE TABLE IF NOT EXISTS generated_history (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    company_id uuid NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    content_type varchar(100) NOT NULL,
    reference_id uuid,
    content text NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_generated_history_company_id ON generated_history(company_id);
