-- Set default campus address for events where address is missing
UPDATE event
SET location_address = 'Vel Tech Rangarajan Dr. Sagunthala R&D Institute of Science and Technology42, Avadi-Vel Tech Road, Vel Nagar, Avadi, Chennai - 600 062, Tamil Nadu, India.'
WHERE location_address IS NULL OR TRIM(location_address) = '';
