-- Database initialization script for Lab2 Video Games
-- This script will populate reference tables (genres, developers, publishers) with initial data

-- Insert Genres
INSERT INTO genres (name, description) VALUES
('Action', 'Fast-paced games requiring hand-eye coordination and quick reflexes'),
('RPG', 'Role-playing games where players control characters in a fictional world'),
('Strategy', 'Games requiring careful planning and tactical thinking'),
('Adventure', 'Story-driven games focusing on exploration and puzzle-solving'),
('Simulation', 'Games that simulate real-world activities'),
('Sports', 'Games that simulate sports activities'),
('Racing', 'Vehicular racing games'),
('Puzzle', 'Games focused on problem-solving and pattern recognition'),
('FPS', 'First-person shooter games'),
('MMORPG', 'Massively multiplayer online role-playing games')
ON CONFLICT (name) DO NOTHING;

-- Insert Developers
INSERT INTO developers (name, country, founded_year) VALUES
('CD Projekt Red', 'Poland', 1994),
('Bethesda Game Studios', 'United States', 2001),
('Rockstar Games', 'United States', 1998),
('Valve Corporation', 'United States', 1996),
('Epic Games', 'United States', 1991),
('FromSoftware', 'Japan', 1986),
('Naughty Dog', 'United States', 1984)
ON CONFLICT (name) DO NOTHING;

-- Insert Publishers
INSERT INTO publishers (name, country, founded_year) VALUES
('Electronic Arts', 'United States', 1982),
('Activision Blizzard', 'United States', 2008),
('Ubisoft', 'France', 1986),
('Sony Interactive Entertainment', 'Japan', 1993),
('Microsoft Gaming', 'United States', 2001),
('CD Projekt', 'Poland', 1994),
('Take-Two Interactive', 'United States', 1993)
ON CONFLICT (name) DO NOTHING;
