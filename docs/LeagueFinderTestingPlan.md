# League Finder Testing Plan

**Project Name:** League Finder  
**Version:** 1.0  
**Date:** 2026-07-23  
**Team:** Amari Ames — Provider, De'saun Avent — Customer  
**Purpose:** Test the completed League Finder user stories and database persistence.

## Actors

- **Provider P:** Team captain or league organizer
- **Customer C:** Player searching for local sports opportunities
- **Listing L:** League, team, game, or roster opportunity

## Use Cases

### 1. Customer US-1 — Create and Manage Profile

1. Customer C1 creates a profile with a name, email, location, and favorite sport.
2. C1 updates their profile information.
3. C1 refreshes the page.

**Expected:** The updated profile remains stored and visible.

### 2. Customer US-2 — Find a League by Sport

1. C1 selects a sport from the homepage search.
2. C1 views leagues matching the selected sport.
3. C1 opens a league details page.

**Expected:** Matching leagues and their database information are displayed.

### 3. Customer US-3 — View Upcoming Matches

1. C1 opens the league schedule.
2. C1 views each game's date, time, teams, and venue.
3. Provider P1 changes a game's date or time.
4. C1 refreshes the schedule.

**Expected:** C1 sees the updated schedule.

### 4. Customer US-4 — View Team Records

1. C1 opens the Team Records page.
2. C1 views each team's wins, losses, and ties.

**Expected:** Team records are retrieved from the backend and displayed.

### 5. Provider US-5 — Manage Roster and Schedule

1. Provider P1 opens Roster & Schedule Management.
2. P1 approves or declines a team registration request.
3. P1 creates a scheduled game.
4. P1 edits the game's date, time, or venue.
5. P1 refreshes the page.

**Expected:** Roster and schedule changes remain saved.

### 6. Provider US-6 — Create Listings

1. P1 creates a league with its sport, location, dates, and description.
2. P1 creates a team associated with that league.
3. Customer C1 browses the available leagues and teams.
4. P1 updates or deletes temporary demonstration records.

**Expected:** Provider listings are saved and visible to customers.

### 7. Provider US-7 — View Customer Statistics

1. P1 opens the Provider Dashboard.
2. P1 views team totals, registrations, approved players, pending requests, and games.
3. P1 approves a temporary request and refreshes the dashboard.

**Expected:** Dashboard statistics reflect the current database information.

### 8. Customer Registration and Reviews

1. C1 requests to join a team.
2. P1 approves the request.
3. C1 submits a review.
4. P1 responds to the review.
5. C1 refreshes the Reviews page.

**Expected:** Registration status, review, and provider response remain visible.

## Cross-Cutting Scenarios

### Performance

1. Load Browse Leagues and the Provider Dashboard five times.
2. Measure response times after Render is awake.

**Expected:** Most pages load within two seconds.

### Persistence

1. Create or update a temporary record.
2. Refresh the page and verify it remains visible.
3. Confirm the information in Neon.
4. Delete the temporary record.

**Expected:** Data remains stored until explicitly deleted.

### Security

1. Confirm database credentials are absent from GitHub and frontend JavaScript.
2. Confirm Render uses environment variables.

**Expected:** Database credentials are not publicly exposed.

### Usability

1. A customer searches for and joins a team.
2. A provider creates a league, team, and game.
3. Record the completion time.

**Expected:** Each actor completes their main flow within five minutes.
