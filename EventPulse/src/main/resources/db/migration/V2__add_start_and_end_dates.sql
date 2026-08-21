-- Rename event_date → start_date and add nullable end_date.
-- Preserves existing row data; does not recreate the table.

ALTER TABLE event RENAME COLUMN event_date TO start_date;

ALTER TABLE event ADD COLUMN end_date DATE;
