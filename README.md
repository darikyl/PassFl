
# Passenger Flow

The Passenger Flow API provides functionality to track and manage passenger coordinates, bus stop information, and related interactions between passengers and bus stops. This API allows users to store and query data related to bus stops, passenger locations, and paths taken by passengers. It integrates with a PostgreSQL database and uses the PostGIS extension for handling geographical data.

## Key Features

- Bus Stop Management: Create and retrieve bus stops with geographical coordinates.
- Passenger Tracking: Track passenger locations with timestamps, and associate them with specific bus stops.
- Geospatial Queries: Find nearby bus stops within a specified radius from a passenger's location.
- Real-time Data: Fetch recent passenger coordinates and bus stop data.

## Database Structure

- bus_stop: Stores bus stop data, including coordinates and names.
- passenger_coordinates: Stores coordinates of passengers along with timestamps and associated bus stops.
- path_part: Stores parts of paths between bus stops for mapping passenger routes.
