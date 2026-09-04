-- V6: create content_ideas table

CREATE TABLE IF NOT EXISTS content_ideas (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    company_id uuid NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    ideas text NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_content_ideas_company_id ON content_ideas(company_id);
