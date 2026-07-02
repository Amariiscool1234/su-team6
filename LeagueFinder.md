Requirements – League Finder

Project Name: League Finder
Team: Amari Ames - Provider, Desaun Avent - Customer
Course: CSC 340
Version: 1.0
Date: July 2026

1. Overview

Vision. League Finder is built to solve a common problem in recreational sports: players often don’t have a team, and team captains often don’t have enough players. This platform connects both sides by allowing players to find leagues or teams to join, while captains can post listings and manage their rosters.

Instead of relying on word-of-mouth or last-minute group chats, League Finder gives users a structured way to find games, join teams, and stay active.

Glossary Terms used in the project

Player / Customer: Someone looking to join a team, league, or sub into games
Provider: A team captain or league organizer who creates listings and manages players
Free Agent: A player who signs up without a specific team and can be matched where needed
Roster: The list of players currently on a team
Listing: A posted league or team opportunity created by a provider

Primary Users / Roles

Customer — Finds and joins leagues, signs up as a sub, and leaves reviews
Provider — Creates listings, manages roster, and responds to players
SysAdmin — Maintains the platform and ensures everything runs properly

Scope (this semester)

User profiles (customer and provider)
Searching and browsing leagues
Requesting to join a team or league
Basic provider dashboard (roster overview)
Reviews and responses

Out of scope (deferred)

Real-time messaging/chat
Payment processing
Advanced player statistics tracking

This document focuses on requirements only. Design details like UI layout and database structure will be handled separately.

2. Functional Requirements (User Stories)
2.1 Customer Stories

US-1 - Register & create profile

Story: As a customer, I want to create an account so that I can join leagues and teams.

Acceptance:

Scenario: Register with valid information
  Given I am not registered
  When I enter valid account details
  Then my account should be created successfully

US-2 - Browse leagues

Story: As a customer, I want to browse available leagues so that I can find one that fits me.

Acceptance:

Scenario: View available leagues
  Given I am on the browse page
  When I search or filter leagues
  Then I should see a list of matching leagues

US-3 - Request to join a league

Story: As a customer, I want to request to join a league so that I can participate.

Acceptance:

Scenario: Request to join a league
  Given I am logged in
  When I select a league and click join
  Then my request should be sent to the provider

US-4 - Leave a review

Story: As a customer, I want to leave a review after playing so others can know about the experience.

Acceptance:

Scenario: Submit a review
  Given I have participated in a league
  When I submit a review
  Then the review should be saved and visible
2.2 Provider Stories

US-5 - Create a listing

Story: As a provider, I want to post a league so players can join.

Acceptance:

Scenario: Create a new listing
  Given I am logged in as a provider
  When I fill out listing details
  Then the listing should be published

US-6 - Manage roster

Story: As a provider, I want to manage my team roster so I can track players.

Acceptance:

Scenario: View roster
  Given I have created a league
  When I open the dashboard
  Then I should see a list of players who joined

US-7 - Respond to join requests

Story: As a provider, I want to accept or reject players so I can control my team.

Acceptance:

Scenario: Respond to a request
  Given I have pending join requests
  When I accept or reject a request
  Then the player should be notified

US-8 - Reply to reviews

Story: As a provider, I want to respond to reviews so I can interact with players.

Acceptance:

Scenario: Reply to a review
  Given a review is posted
  When I respond
  Then my response should be visible under the review
3. Non-Functional Requirements
Performance: Pages should load within 2–3 seconds under normal use
Availability: The system should be accessible at all times except during maintenance
Security: User accounts must be protected with basic authentication
Usability: A new user should be able to register and find a league within a few minutes
4. Assumptions, Constraints, and Policies
Users will access the app through modern browsers
The system is built within the constraints of the course timeline
No real payment or advanced backend features are required
5. Milestones (course-aligned)
M1 Requirements — SRS document completed
M2 Prototype — UI pages created and connected
M3 Design — system structure planned
M4 Backend — basic functionality implemented
M5 Increment — core features working
M6 Final — full system completed
6. Change Management
Changes will be tracked through GitHub commits and pull requests
Updates to requirements will be reflected in this document when needed