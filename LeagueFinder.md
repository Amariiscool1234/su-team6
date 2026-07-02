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
- **US-1 - Create and manage customer profile**

  _Story:_ As a customer, I want to create a profile with my location, preferred sports, availability, and skill level, so that I get matched with opportunities that actually fit me instead of scrolling through everything.

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

- **US-2 - Browse available services**

  _Story:_ As a customer, I want to search teams, leagues, and sub opportunities by sport, location, date, and skill level, so that I only see listings that are actually relevant to me.

  _Acceptance:_
```gherkin
  Scenario: Browse services by filter
    Given I am logged in as a customer
    When I filter by sport, location, date, or skill level
    Then I should see a list of matching teams, leagues, and sub opportunities
```

- **US-3 - Subscribe to a service**

  _Story:_ As a customer, I want to request to join a team, sign up as a free agent, or offer to sub for a game, so that I can start playing and stay updated on upcoming games.

  _Acceptance:_
```gherkin
  Scenario: Request to join a team
    Given I am logged in as a customer
    When I request to join a team or offer to sub for a game
    Then the provider should be notified of my request
    And I should receive a confirmation once accepted

  Scenario: Receive game reminders
    Given I have been accepted onto a team
    When a game is approaching
    Then I should receive a reminder notification
```

- **US-4 - Write a review after playing**

  _Story:_ As a customer, I want to leave a review after participating in a game or joining a team, so that other players have a better idea of what to expect.

  _Acceptance:_
```gherkin
  Scenario: Write a review after participating
    Given I have participated in a game or joined a team
    When I submit a review with a rating and comments
    Then the review should be saved and visible to other customers
```

### 2.2 Provider Stories

- **US-5 - Create, update, or remove provider profile**

  _Story:_ As a provider, I want to create, update, and remove my profile — including team info, contact info, sport type, and location — so that my information stays accurate and I can take it down if I stop running the team.

  _Acceptance:_
```gherkin
  Scenario: Create provider profile
    Given I do not have a profile
    When I provide my team/league details and submit the form
    Then my profile should be created and visible to customers

  Scenario: Remove provider profile
    Given I have an existing provider profile
    When I choose to delete my profile
    Then my profile and associated listings should no longer be visible to customers
```

- **US-6 - Create a service listing**

  _Story:_ As a provider, I want to post a new team, league, or open roster listing, so that customers can find it and join.

  _Acceptance:_
```gherkin
  Scenario: Create a new listing
    Given I am logged in as a provider
    When I enter listing details (sport, location, date, skill level, open spots) and submit
    Then the listing should be saved and visible to customers browsing services
```

- **US-7 - View customer statistics**

  _Story:_ As a provider, I want to see how many players have joined, requested to join, or signed up to sub for my teams, so that I know where my roster actually stands.

  _Acceptance:_
```gherkin
  Scenario: View roster statistics
    Given I am logged in as a provider
    When I access my team dashboard
    Then I should see the number of joined players, pending requests, and available subs
```

- **US-8 - Reply to reviews**

  _Story:_ As a provider, I want to respond to player reviews, so that I can address feedback or clear up anything that got miscommunicated.

  _Acceptance:_
```gherkin
  Scenario: Reply to a review
    Given a customer has left a review on one of my listings
    When I submit a reply to that review
    Then my reply should be saved and visible alongside the original review
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