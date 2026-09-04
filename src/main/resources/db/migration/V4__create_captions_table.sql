-- V4: create captions table

CREATE TABLE IF NOT EXISTS captions (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    company_id uuid NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    platform varchar(100) NOT NULL,
    content_type varchar(100) NOT NULL,
    tone varchar(255),
    content text NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_captions_company_id ON captions(company_id);
