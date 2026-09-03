-- V4: fix BCrypt hashes seeded by V3
--
-- V3 incorrectly stored doubled dollar signs in password_hash values, so the
-- hashes were not valid BCrypt and login failed for the seeded demo users.
-- This migration replaces them with valid BCrypt hashes (cost 10).
-- Demo plaintext passwords are documented in README only.

UPDATE app_user
SET password_hash = '$2a$10$AIACvUD9Bqo5CVrCPbB8yuygytxCgatzqPg/h4gfa606J3pUJtwu.'
WHERE username = 'admin';

UPDATE app_user
SET password_hash = '$2a$10$cKz9KiES6C82yhB.Zve3/OGFuZoud7ts9IPzXdbk9NcQol4KQ4SlC'
WHERE username = 'viewer';
