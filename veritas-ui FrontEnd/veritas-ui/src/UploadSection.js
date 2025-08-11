import React, { useState } from "react";

function UploadSection() {
  const [selectedFile, setSelectedFile] = useState(null);
  const [modelType, setModelType] = useState("Random Forest");
  const [learningType, setLearningType] = useState("Supervised");

  const [sessionId, setSessionId] = useState("");
  const [trainingOutput, setTrainingOutput] = useState("");
  const [auditData, setAuditData] = useState(null);
  const [syntheticRows, setSyntheticRows] = useState([]);

  const handleTrain = async () => {
    if (!selectedFile) return alert("Please select a CSV file first!");

    const formData = new FormData();
    formData.append("file", selectedFile);
    formData.append("modelType", modelType);
    formData.append("learningType", learningType);

    try {
      const response = await fetch("http://localhost:8080/train", {
        method: "POST",
        body: formData,
      });

      const text = await response.text();
      setTrainingOutput(text);

      const sessionLine = text.split(" Session ID: ")[1];
      if (sessionLine) {
        const id = sessionLine.trim();
        setSessionId(id);
      }

    } catch (error) {
      console.error("Training error:", error);
      alert("Training failed. See console.");
    }
  };

  const handleAudit = async () => {
    if (!sessionId) return alert("No session ID found!");

    try {
      const response = await fetch(`http://localhost:8080/audit/${sessionId}`);
      const data = await response.json();
      setAuditData(data);
    } catch (error) {
      console.error("Audit error:", error);
      alert("Audit failed.");
    }
  };

  const handleSynthetic = async () => {
    if (!sessionId) return alert("No session ID found!");

    try {
      const response = await fetch(`http://localhost:8080/synthetic/${sessionId}`);
      const data = await response.json();
      setSyntheticRows(data.syntheticRows || []);
    } catch (error) {
      console.error("Synthetic error:", error);
      alert("Synthetic generation failed.");
    }
  };

  return (
    <div className="p-6 max-w-4xl mx-auto bg-white shadow rounded">
      <h2 className="text-xl font-bold mb-4">Upload CSV & Train Model</h2>

      <input type="file" onChange={(e) => setSelectedFile(e.target.files[0])} />
      
      <div className="mt-4 flex gap-4">
        <select value={modelType} onChange={(e) => setModelType(e.target.value)}>
          <option>Linear Regression</option>
                    <option>Logistic Regression</option>
          <option>Decision Tree</option>
          <option>Random Forest</option>
          <option>Support Vector Machine</option>
          <option>Linear Neighbors</option>
          <option>Naive Bayes</option>
          <option>Neural Network</option>
          <option>K-Means Clustering</option>
          <option> DBSCAN</option>
          <option>PCA</option>
          <option>Q-Learning</option>
          <option>Deep Q Network</option>

        </select>

        <select value={learningType} onChange={(e) => setLearningType(e.target.value)}>
          <option>Supervised</option>
          <option>Unsupervised</option>
          <option>Semi-Supervised</option>
          <option>Reinforcement</option>
        </select>
      </div>

      <button onClick={handleTrain} className="mt-4 bg-blue-600 text-white px-4 py-2 rounded">
        Train
      </button>

      {trainingOutput && (
        <div className="mt-4 p-3 border bg-gray-100 whitespace-pre-wrap rounded">
          {trainingOutput}
        </div>
      )}

      {sessionId && (
        <div className="mt-6 space-x-4">
          <button onClick={handleAudit} className="bg-purple-600 text-white px-3 py-2 rounded">Audit</button>
          <button onClick={handleSynthetic} className="bg-green-600 text-white px-3 py-2 rounded">Generate Synthetic</button>
        </div>
      )}

      {auditData && (
        <div className="mt-4">
          <h3 className="font-semibold">Audit Report</h3>
          <p>Total Rows: {auditData.totalRows}</p>

          <h4 className="mt-2">Null Count Per Column:</h4>
          <ul>
            {Object.entries(auditData.nullCountPerColumn || {}).map(([key, value]) => (
              <li key={key}>{key}: {value}</li>
            ))}
          </ul>

          <h4 className="mt-2">Unique Count Per Column:</h4>
          <ul>
            {Object.entries(auditData.uniqueCountPerColumn || {}).map(([key, value]) => (
              <li key={key}>{key}: {value}</li>
            ))}
          </ul>
        </div>
      )}

      {syntheticRows.length > 0 && (
        <div className="mt-4">
          <h3 className="font-semibold">Synthetic Data (5 rows)</h3>
          <table className="table-auto border-collapse border border-gray-400 mt-2">
            <thead>
              <tr>
                {Object.keys(syntheticRows[0]).map((col) => (
                  <th key={col} className="border border-gray-300 px-2 py-1">{col}</th>
                ))}
              </tr>
            </thead>
            <tbody>
              {syntheticRows.map((row, index) => (
                <tr key={index}>
                  {Object.values(row).map((val, i) => (
                    <td key={i} className="border border-gray-300 px-2 py-1">{val}</td>
                  ))}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default UploadSection;
