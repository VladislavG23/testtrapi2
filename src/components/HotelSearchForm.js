import React, { useState } from 'react';

const HotelSearchForm = () => {
  const [checkIn, setCheckIn] = useState('');
  const [checkOut, setCheckOut] = useState('');
  const [rooms, setRooms] = useState(1);
  const [adults, setAdults] = useState(1);
  const [children, setChildren] = useState(0);
  const [placeName, setPlaceName] = useState('');
  const [radius, setRadius] = useState(10);
  const [unit, setUnit] = useState('km');
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    if (!checkIn || !checkOut) {
      setError('Check-in and check-out dates are required.');
      return;
    }

    if (adults < 1) {
      setError('At least one adult is required.');
      return;
    }

    try {
      const coordinates = await getCoordinates(placeName);
      const requestBody = {
        stay: {
          checkIn,
          checkOut,
        },
        occupancies: [
          {
            rooms,
            adults,
            children,
          },
        ],
        geolocation: {
          latitude: coordinates.latitude,
          longitude: coordinates.longitude,
          radius,
          unit,
        },
      };

      const response = await fetch('/api/hotelbeds-hotels-booking-hotel-availability', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestBody),
      });

      if (!response.ok) {
        throw new Error('Failed to fetch hotel availability.');
      }

      const data = await response.json();
      console.log('Hotel availability:', data);
    } catch (error) {
      console.error('Error:', error);
      setError(error.message);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Check-In Date:</label>
        <input type="date" value={checkIn} onChange={(e) => setCheckIn(e.target.value)} required />
      </div>
      <div>
        <label>Check-Out Date:</label>
        <input type="date" value={checkOut} onChange={(e) => setCheckOut(e.target.value)} required />
      </div>
      <div>
        <label>Rooms:</label>
        <input type="number" value={rooms} onChange={(e) => setRooms(e.target.value)} min="1" required />
      </div>
      <div>
        <label>Adults:</label>
        <input type="number" value={adults} onChange={(e) => setAdults(e.target.value)} min="1" required />
      </div>
      <div>
        <label>Children:</label>
        <input type="number" value={children} onChange={(e) => setChildren(e.target.value)} min="0" />
      </div>
      <div>
        <label>Place Name:</label>
        <input type="text" value={placeName} onChange={(e) => setPlaceName(e.target.value)} required />
      </div>
      <div>
        <label>Radius (km):</label>
        <input type="number" value={radius} onChange={(e) => setRadius(e.target.value)} min="1" required />
      </div>
      <button type="submit">Search Hotels</button>
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </form>
  );
};

const getCoordinates = async (placeName) => {
  const apiKey = process.env.REACT_APP_GOOGLE_MAPS_API_KEY;
  const geocodeUrl = `https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(placeName)}&key=${apiKey}`;
  try {
    const response = await fetch(geocodeUrl);
    const data = await response.json();
    console.log('Geocoding response:', data);
    if (data.results.length > 0) {
      const location = data.results[0].geometry.location;
      return {
        latitude: location.lat,
        longitude: location.lng,
      };
    } else {
      throw new Error('No results found for the specified place name.');
    }
  } catch (error) {
    console.error('Geocoding error:', error);
    throw error;
  }
};

export default HotelSearchForm;
