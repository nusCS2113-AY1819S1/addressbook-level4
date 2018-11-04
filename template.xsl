<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
<html>
<body>
  <table border="1">
    <tr bgcolor="#95afc0">
      <th style="text-align:left">Name</th>
      <th style="text-align:left">Quantity</th>
<th style="text-align:left">Minimum Quantity</th>
<th style="text-align:left">Status-Ready</th>
<th style="text-align:left">Status-On Loan</th>
<th style="text-align:left">Status-Faulty</th>
    </tr>
    <xsl:for-each select="stocklist/items">
    <tr>
      <td><xsl:value-of select="name"/></td>
      <td><xsl:value-of select="quantity"/></td>
<td><xsl:value-of select="minQuantity"/></td>
<xsl:for-each select="status">
  <td><xsl:value-of select="statusReady"/></td>
  <td><xsl:value-of select="statusFaulty"/></td>
  <td><xsl:value-of select="statusOnLoan"/></td>
</xsl:for-each>
    </tr>
    </xsl:for-each>
  </table>
</body>
</html>
</xsl:template>
</xsl:stylesheet>
