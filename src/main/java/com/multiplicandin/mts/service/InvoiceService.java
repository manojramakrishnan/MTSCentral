package com.multiplicandin.mts.service;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import com.multiplicandin.mts.model.CustomerOrder;

public interface InvoiceService {

	File generateInvoiceFor(CustomerOrder customerOrder, Locale forLanguageTag) throws IOException;

}
