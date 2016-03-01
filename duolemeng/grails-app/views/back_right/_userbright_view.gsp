<g:render template="/back_right/basicright" />

<button type="button" data-dojo-type="dijit/form/Button" data-dojo-props="iconClass:'dijitIconDelete'"
        id='btn_userbright_close' style="margin-left: 6em" >关闭
    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
        dijit.byId('dlg_userbright').hide();
    </script>
</button>
