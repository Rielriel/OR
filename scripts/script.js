
$(document).ready(function() {
    let table = $('#dataTable').DataTable();

    function fetchData(searchText = "", searchField = "all") {
        $.ajax({
            url: 'http://localhost:8080/klub',
            method: 'GET',
            data: {
                searchText: searchText,
                searchField: searchField
            },
            success: function(klubovi) {
                table.clear();
                klubovi.forEach(function(klub) {
                    table.row.add([
                        klub.naziv_liga,
                        klub.rang,
                        klub.broj_klubova,
                        klub.krugovi,
                        klub.naziv_klub,
                        klub.nadimak,
                        klub.naziv_stadion,
                        klub.mjesto,
                        klub.godina_osnutak,
                        klub.predsjednik,
                        klub.trener,
                        klub.navijači,
                        klub.boja,
                        klub.prvak_hrvatska
                    ]).draw(false);
                });
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error("Greška prilikom dohvata podataka:");
                console.log("Status: ", textStatus);
                console.log("Greška: ", errorThrown);
                console.log("Detalji odgovora: ", jqXHR.responseText);
            }
        });
    }



    fetchData();


    $('#filterForm').submit(function(e) {
        e.preventDefault();
        let searchText = $('#searchText').val().toLowerCase();
        let searchField = $('#searchField').val();
        fetchData(searchText, searchField);
    });


    $('#clearFilter').click(function() {
        $('#searchText').val('');
        $('#searchField').val('all');
        fetchData();
    });


    function downloadCSV() {
        let rows = table.rows({ filter: 'applied' }).data();
        let csv = [];
  
        csv.push(table.columns().header().toArray().map(header => header.textContent).join(','));


        rows.each(function(rowData) {
            csv.push(rowData.join(','));
        });


        let csvString = csv.join('\n');
        let blob = new Blob([csvString], { type: 'text/csv' });
        let url = URL.createObjectURL(blob);
        let a = document.createElement('a');
        a.href = url;
        a.download = 'klubovi.csv';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    }

    function downloadJSON() {
        let rows = table.rows({ filter: 'applied' }).data();
        let data = [];

        rows.each(function(rowData) {
            let rowObject = {
                "Naziv": rowData[4],
                "Podatci": {
                    "Nadimak": rowData[5],
                    "Stadion": rowData[6],
                    "Mjesto": rowData[7], 
                    "GodinaOsnutka": rowData[8],
                    "Predsjednik": rowData[9],
                    "Trener": rowData[10],
                    "NavijačkaSkupina": rowData[11],
                    "BojaDresa": rowData[12],
                    "PrvakHrvatske": rowData[13],
                    "Liga": {
                        "NazivLige": rowData[0],
                        "Rang": rowData[1],
                        "BrojKlubova": rowData[2],
                        "Krugovi": rowData[3]
                    }
                }
            };
            data.push(rowObject);
        });

        
        let jsonString = JSON.stringify(data, null, 2);
        let blob = new Blob([jsonString], { type: 'application/json' });
        let url = URL.createObjectURL(blob);
        let a = document.createElement('a');
        a.href = url;
        a.download = 'klubovi.json';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    }

    $('#downloadCSV').click(downloadCSV);
    $('#downloadJSON').click(downloadJSON);

});
