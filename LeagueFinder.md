# Requirements – League Finder

**Project Name:** League Finder \
**Team:** Amari Ames - Provider, Desaun Avent - Customer \
**Course:** CSC 340\
**Version:** 1.0\
**Date:** 2026-07-01 

---

## 1. Overview
**Vision.** League Finder is a local recreational sports platform that helps players find teams, leagues, and substitute opportunities near them, while giving team captains and league organizers an easy way to fill open roster spots. A lot of people want to play rec sports but don't already know a team to join, and captains are constantly short players trying to fill a roster before game day — League Finder connects those two groups directly.

**Glossary** Terms used in the project
- **Player / Customer:** A person looking to join a team, league, or sub opportunity.
- **Provider:** A team captain or league organizer who manages a roster and posts openings.
- **Profile:** A collection of information about a user, including personal details, location, sport preferences, availability, and skill level.
- **Service:** A team, league, or open roster/sub listing posted by a provider.
- **Free Agent:** A customer who signs up without picking one specific team, so they can be matched to whichever team needs a player.
- **Roster:** The current list of players on a team.

**Primary Users / Roles.**
- **Customer** - Find teams, leagues, and sub opportunities that match their sport, location, and skill level.
- **Provider** — Post listings, fill roster spots, and manage player engagement.
- **SysAdmin** — Maintain platform quality and security.

**Scope (this semester).**
- User profiles (customers & providers)
- Search/browse teams, leagues, and open roster or sub spots
- Requesting to join a team, signing up as a free agent, or offering to sub
- Provider dashboard with basic roster statistics
- Reviews and review replies

**Out of scope (deferred).**
- Payment processing / league fees
- Live in-game score tracking
- Native mobile app

> This document is **requirements‑level** and solution‑neutral; design decisions (UI layouts, API endpoints, schemas) are documented separately.

---

## 2. Functional Requirements (User Stories)

### 2.1 Customer Stories
- **US-1 - Create a profile**

  _Story:_ As a customer, I want to create a profile, so that I can find a local team in my area to play with.

  _Acceptance:_
```gherkin
  Scenario: Register with valid credentials
    Given I am not registered
    When I provide valid registration details
    Then I should be successfully registered and logged in

  Scenario: Update profile information
    Given I am logged in as a customer
    When I edit my location, sport preferences, or skill level and save
    Then my profile should reflect the updated information
```

- **US-2 - Find a league by sport**

  _Story:_ As a customer, I want to find a league with my favorite sport of choice, so that I can quickly locate something relevant to me.

  _Acceptance:_
```gherkin
  Scenario: Browse services by filter
    Given I am logged in as a customer
    When I filter by sport, location, date, or skill level
    Then I should see a list of matching teams, leagues, and sub opportunities
```

- **US-3 - See upcoming matches**

  _Story:_ As a customer, I want to see upcoming matches, so that I know when to play my match.

  _Acceptance:_
```gherkin
  Scenario: View upcoming matches
    Given I am logged in as a customer and on a team
    When I open my schedule
    Then I should see a list of my upcoming games with date and time

  Scenario: Receive game reminders
    Given I have an upcoming match
    When the game is approaching
    Then I should receive a reminder notification
```

- **US-4 - View league standings**

  _Story:_ As a customer, I want to view the league standings, so that I know what my team's record is.

  _Acceptance:_
```gherkin
  Scenario: View standings
    Given I am logged in as a customer and on a team
    When I open the league standings page
    Then I should see my team's wins, losses, and current rank
```

### 2.2 Provider Stories

- **US-5 - Make roster and schedule updates**

  _Story:_ As a provider, I want to make updates such as team substitutions and game scheduling, so that my roster and schedule stay accurate.

  _Acceptance:_
```gherkin
  Scenario: Update team roster or schedule
    Given I am logged in as a provider
    When I make a substitution or update a game's date/time
    Then the change should be saved and reflected for players on that team
```

- **US-6 - Create a new listing**

  _Story:_ As a provider, I want to create new teams, leagues, or open roster listings, so that customers can find and join them.

  _Acceptance:_
```gherkin
  Scenario: Create a new listing
    Given I am logged in as a provider
    When I enter listing details (sport, location, date, skill level, open spots) and submit
    Then the listing should be saved and visible to customers browsing services
```

- **US-7 - View customer statistics**

  _Story:_ As a provider, I want to view how many players have joined, requested to join, or signed up as substitutes, so that I know where my roster stands.

  _Acceptance:_
```gherkin
  Scenario: View roster statistics
    Given I am logged in as a provider
    When I access my team dashboard
    Then I should see the number of joined players, pending requests, and available subs

```

---

## 3. Non‑Functional Requirements
- **Performance:** 95% of search/browse responses should be returned in < 2 seconds under typical load.
- **Availability/Reliability:** The system should be available 99.5% of the time, with planned maintenance windows communicated in advance.
- **Security/Privacy:** The system must implement secure authentication and authorization mechanisms. All sensitive data should be encrypted in transit and at rest.
- **Usability:** New users should be able to complete registration and either browse a team or post a listing within 5 minutes without external assistance.

---

## 4. Assumptions, Constraints, and Policies
- Modern browsers (latest Chrome/Firefox/Edge/Safari); stable connectivity.
- Course timeline & campus infrastructure constraints apply.
- Users are assumed to be 18+ or have appropriate guardian consent to participate in local rec leagues.

---

## 5. Milestones (course‑aligned)
- **M1 Requirements** — this file + stories opened as issues.
- **M2 High‑fidelity prototype** — core customer/provider UI flows fully interactive.
- **M3 Design** — architecture, schema, API outline.
- **M4 Backend API** — key endpoints + tests.
- **M5 Increment** — ≥2 use cases end‑to‑end.
- **M6 Final** — complete system & documentation.

---

## 6. Change Management
- Stories are living artifacts; changes are tracked via repository issues and linked pull requests.
- Major changes should update this SRS.