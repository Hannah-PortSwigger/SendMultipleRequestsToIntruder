import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.ui.contextmenu.InvocationType;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class Extension implements BurpExtension
{
    @Override
    public void initialize(MontoyaApi montoyaApi)
    {
        String extensionName = "Send multiple requests to Intruder";

        montoyaApi.extension().setName(extensionName);

        montoyaApi.userInterface().registerContextMenuItemsProvider(contextMenuEvent ->
        {
            if (contextMenuEvent.isFrom(InvocationType.PROXY_HISTORY))
            {
                JMenuItem menuItem = new JMenuItem(extensionName);

                menuItem.addActionListener(l -> {
                    List<HttpRequestResponse> requestResponses = contextMenuEvent.selectedRequestResponses();

                    requestResponses.forEach(r -> montoyaApi.intruder().sendToIntruder(r.request()));
                });

                return List.of(menuItem);
            }

            return Collections.emptyList();
        });
    }
}
