-- V8: create content_calendar table

CREATE TABLE IF NOT EXISTS content_calendar (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    company_id uuid NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    entries text NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_content_calendar_company_id ON content_calendar(company_id);
