const app = require("express")();
const express = require("express");
const {HTTP} = require("cloudevents");
const axios = require("axios");

app.use(express.json())

app.get("/events/latest", async (req, res) => {
    console.log("Received latest event request");
    console.log("Fetching latest cloud event from spring backend");
    const response= await axios.get('http://localhost:8080/events/latest');
    const cloudEvent = HTTP.toEvent({headers: response.headers, body: response.data});
    console.log("Received cloud event from spring backend", cloudEvent);
    res.json(cloudEvent)
});

app.listen(8081, () => {
    console.log("Started on http://localhost:8081")
});