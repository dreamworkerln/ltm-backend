package ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base;

public class HandlerName {

    // classic jpa CRUD
    private static final String findById = "findById";
    private static final String findAllById = "findAllById";
    private static final String findAll = "findAll";
    private static final String findFirst = "findFirst";

    private static final String save = "save";
    private static final String delete = "delete";
    private static final String put = "put";
    private static final String remove = "remove";

    // common
    private static final String getCurrent = "getCurrent";


    // calculator specific
    private static final String add = "add";
    private static final String sub = "sub";
    private static final String mul = "mul";
    private static final String div = "div";
    private static final String ssuper ="ssuper";
    private static final String zoper ="zoper";

    

    // geodata specific
    private static final String getRoute = "getRoute";

    // user specific
    private static final String findByUsername = "findByUsername";
    private static final String makeClient = "makeClient";
    private static final String makeCourier = "makeCourier";

    // task specific
    //private static final String findByCurrentUser = "findByCurrentUser";
    private static final String findByUser = "findByUser";
    private static final String findExperimental = "findExperimental";

    // order specific
    private static final String cancel = "cancel";
    private static final String close = "close";


    // order courier specific
    public static final String findNew = "findNew";
    public static final String accept = "accept";
    public static final String execute = "execute";
    private static final String complete = "complete";

    //payment
    //public static final String addCashFlow="addCashFlow";
    //public static final String getCashFlow="getCashFlow";

    // ------------------------------------------------------------


    // CALCULATOR -------------------------------------------------
    public static class calculator {
        public static final String path = "calculator.actions";

        public static final String add = HandlerName.add;
        public static final String sub = HandlerName.sub;
        public static final String mul = HandlerName.mul;
        public static final String div = HandlerName.div;
        public static final String ssuper = HandlerName.ssuper;
        public static final String zoper = HandlerName.zoper;


    }

    // USER --------------------------------------------------------

    public static class user {

        public static final String path = "ltmapp.core.user";
        public static final String getCurrent = HandlerName.getCurrent;    // current user tasks
        public static final String save = HandlerName.save;
        public static final String makeClient = HandlerName.makeClient;
        public static final String makeCourier = HandlerName.makeCourier;
    }


    // TASK  --------------------------------------------------------

    public static class task {

        public static final String path = "ltmapp.core.task";
        public static final String findAll = HandlerName.findAll;
        public static final String findById = HandlerName.findById;
        public static final String findByUser = HandlerName.findByUser;  // used by manager
        public static final String save = HandlerName.save;
        //experimental ACL
        public static final String findExperimental = HandlerName.findExperimental;


    }

    // CLIENT --------------------------------------------------------


    public static class client {

        public static final String path = "ltmapp.core.client";
        public static final String getCurrent = HandlerName.getCurrent;
        public static final String save = HandlerName.save;
    }


    // COURIER --------------------------------------------------------


    public static class courier {

        public static final String path = "ltmapp.core.courier";
        public static final String getCurrent = HandlerName.getCurrent;
        public static final String save = HandlerName.save;
    }

    // ORDER -------------------------------------------------------

    public static class order {

        public static class courier {
            public static final String path = "ltmapp.core.order.courier";

            public static final String findById = HandlerName.findById;
            public static final String findAllById = HandlerName.findAllById;
            public static final String findAll = HandlerName.findAll;
            public static final String findFirst = HandlerName.findFirst;
            public static final String findNew = HandlerName.findNew;
            //public static final String save = HandlerName.save;
            //public static final String delete = HandlerName.delete;
            public static final String accept = HandlerName.accept;
            public static final String execute = HandlerName.execute;
            public static final String complete = HandlerName.complete;
            public static final String close = HandlerName.close;
        }

        public static class client {
            public static final String path = "ltmapp.core.order.client";

            public static final String findById = HandlerName.findById;
            public static final String findAllById = HandlerName.findAllById;
            public static final String findAll = HandlerName.findAll;
            public static final String findFirst = HandlerName.findFirst;
            public static final String save = HandlerName.save;
            public static final String cancel = HandlerName.cancel;
            public static final String delete = HandlerName.delete;
        }
    }


    // MANAGEMENT ------------------------------------------------------

    public static class management {

        public static class order {

            public static final String path = "ltmapp.core.management.order";

            public static final String findById = HandlerName.findById;
            public static final String findAllById = HandlerName.findAllById;
            public static final String findAll = HandlerName.findAll;
            public static final String findFirst = HandlerName.findFirst;
            public static final String save = HandlerName.save;
            public static final String delete = HandlerName.delete;
        }

        public static class user {

            public static final String path = "ltmapp.core.management.user";

            public static final String findById = HandlerName.findById;
            public static final String findAllById = HandlerName.findAllById;
            public static final String findAll = HandlerName.findAll;
            public static final String findByUsername = HandlerName.findByUsername;
            public static final String findFirst = HandlerName.findFirst;
            public static final String save = HandlerName.save;
            public static final String delete = HandlerName.delete;

        }

        public static class client {

            public static final String path = "ltmapp.core.management.client";

            public static final String findById = HandlerName.findById;
            public static final String save = HandlerName.save;
            public static final String findByUsername = HandlerName.findByUsername;

        }

        public static class courier {

            public static final String path = "ltmapp.core.management.courier";

            public static final String findById = HandlerName.findById;
            public static final String save = HandlerName.save;
            public static final String findByUsername = HandlerName.findByUsername;

        }


    }

    // PAYMENT ------------------------------------------------------


    public static class payment {


        public static class cashflow {
            public static final String path = "ltmapp.core.payment.cashflow";
            public static final String findAll = HandlerName.findAll;
            public static final String save = HandlerName.save;
        }
    }

}
