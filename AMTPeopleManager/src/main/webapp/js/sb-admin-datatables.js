// Call the dataTables jQuery plugin
var table;

jQuery(document).ready(function() {
    table = $('#dataTable').dataTable({
        "bPaginate": true,
        "order": [0, 'asc'],
        "bInfo": true,
        "iDisplayStart": 0,
        "bProcessing": true,
        "bServerSide": true,
        "lengthMenu": [[10, 25, 50], [10, 25, 50]],
        "sAjaxSource": path + "/people-data",
    })

});

$("#dataTable_length").hide();